package com.mirego.konnectivity

sealed class NetworkState {
    data class Reachable(
        val metered: Boolean
    ) : NetworkState()

    object Unreachable : NetworkState()
}
