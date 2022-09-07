package com.mirego.konnectivity

import kotlinx.coroutines.flow.Flow

interface Konnectivity {
    val networkState: Flow<NetworkState>
}

expect fun Konnectivity(): Konnectivity
