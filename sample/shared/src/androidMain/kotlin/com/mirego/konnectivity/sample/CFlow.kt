package com.mirego.konnectivity.sample

import kotlinx.coroutines.flow.Flow

actual class CFlow<T : Any?> internal constructor(origin: Flow<T>) : Flow<T> by origin

actual fun <T> Flow<T>.wrap(): CFlow<T> = CFlow(this)
