package com.mirego.konnectivity.sample

import com.mirego.konnectivity.Konnectivity
import com.mirego.konnectivity.NetworkState
import com.mirego.konnectivity.shared
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.map
import kotlin.js.JsExport

@JsExport
@Suppress("NON_EXPORTABLE_TYPE")
object Bootstrap {
    private val applicationScope = MainScope()

    private val konnectivity by lazy { Konnectivity().shared(applicationScope) }

    val networkStateText: CFlow<String>
        get() = konnectivity.networkState.map(NetworkState::describe).wrap()

    val networkStateImageResource: CFlow<KonnectivityImageResource>
        get() = konnectivity.networkState.map(NetworkState::asImageResource).wrap()
}

fun NetworkState.describe(): String =
    when (this) {
        is NetworkState.Reachable -> when (metered) {
            true -> "You're online, but your connection is metered."
            else -> "You're online!"
        }
        NetworkState.Unreachable -> "You're offline."
    }

private fun NetworkState.asImageResource(): KonnectivityImageResource =
    when (this) {
        is NetworkState.Reachable -> when (metered) {
            true -> KonnectivityImageResource.NETWORK_STATE_REACHABLE_METERED
            else -> KonnectivityImageResource.NETWORK_STATE_REACHABLE
        }
        NetworkState.Unreachable -> KonnectivityImageResource.NETWORK_STATE_UNREACHABLE
    }

@JsExport
enum class KonnectivityImageResource {
    NETWORK_STATE_REACHABLE,
    NETWORK_STATE_REACHABLE_METERED,
    NETWORK_STATE_UNREACHABLE,
}
