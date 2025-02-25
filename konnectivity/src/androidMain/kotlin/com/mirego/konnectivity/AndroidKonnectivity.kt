package com.mirego.konnectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

internal class AndroidKonnectivity(context: Context) : Konnectivity {

    private val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .also { builder ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                builder.addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            }
        }
        .build()

    private val availableNetworks = mutableSetOf<Network>()

    override val networkState = callbackFlow {
        trySend(connectivityManager.getCurrentNetworkState())

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                availableNetworks.add(network)

                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

                val networkState = networkCapabilities?.asNetworkState() ?: NetworkState.Unreachable
                trySend(networkState)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)

                val networkState = networkCapabilities.asNetworkState()
                trySend(networkState)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                availableNetworks.remove(network)

                if (availableNetworks.isEmpty()) {
                    trySend(NetworkState.Unreachable)
                }
            }
        }

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    private fun ConnectivityManager.getCurrentNetworkState(): NetworkState {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            val connected = activeNetworkInfo?.isConnected == true
            val metered = isActiveNetworkMetered

            return when {
                connected -> NetworkState.Reachable(metered)
                else -> NetworkState.Unreachable
            }
        }

        val networkCapabilities = getNetworkCapabilities(activeNetwork)

        return networkCapabilities?.asNetworkState() ?: NetworkState.Unreachable
    }

    private fun NetworkCapabilities.asNetworkState(): NetworkState {
        val connected = hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        val metered = !hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)

        return when {
            connected -> NetworkState.Reachable(metered)
            else -> NetworkState.Unreachable
        }
    }
}
