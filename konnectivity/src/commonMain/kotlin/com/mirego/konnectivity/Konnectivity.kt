package com.mirego.konnectivity

import kotlinx.coroutines.flow.Flow

public interface Konnectivity {
    public val networkState: Flow<NetworkState>
}

public expect fun Konnectivity(): Konnectivity
