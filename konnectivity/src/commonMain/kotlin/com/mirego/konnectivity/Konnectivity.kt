package com.mirego.konnectivity

import kotlinx.coroutines.flow.Flow

interface Konnectivity {
    val networkState: Flow<NetworkState>

    interface Factory {
        fun create(): Konnectivity
    }
}
