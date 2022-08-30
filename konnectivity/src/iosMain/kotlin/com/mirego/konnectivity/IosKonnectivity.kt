@file:Suppress("unused")

package com.mirego.konnectivity

import cocoapods.Reachability.NetworkStatus
import cocoapods.Reachability.Reachability
import cocoapods.Reachability.ReachableViaWWAN
import cocoapods.Reachability.ReachableViaWiFi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class IosKonnectivity private constructor() : Konnectivity {

    object Factory : Konnectivity.Factory {
        override fun create(): Konnectivity = IosKonnectivity()
    }

    private val reachability: Reachability? by lazy {
        Reachability.reachabilityForInternetConnection()
    }

    override val networkState = callbackFlow {
        val currentNetworkState = reachability?.currentReachabilityStatus().asNetworkState()
        trySend(currentNetworkState)

        reachability?.reachableBlock = { r ->
            val networkState = r?.currentReachabilityStatus().asNetworkState()
            trySend(networkState)
        }

        reachability?.unreachableBlock = { _ ->
            trySend(NetworkState.Unreachable)
        }

        reachability?.startNotifier()
        awaitClose {
            reachability?.stopNotifier()
        }
    }

    private fun NetworkStatus?.asNetworkState() = when (this) {
        ReachableViaWWAN -> NetworkState.Reachable(true)
        ReachableViaWiFi -> NetworkState.Reachable(false)
        else -> NetworkState.Unreachable
    }
}
