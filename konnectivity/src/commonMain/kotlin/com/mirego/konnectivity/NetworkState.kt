package com.mirego.konnectivity

public sealed class NetworkState {
    public data class Reachable(
        val metered: Boolean?
    ) : NetworkState()

    public object Unreachable : NetworkState()
}
