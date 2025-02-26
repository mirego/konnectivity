package com.mirego.konnectivity.sample

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun interface Closeable {
    fun close()
}

actual class CFlow<T : Any?> internal constructor(
    private val origin: Flow<T>,
    // Immediate dispatching is not supported on Native
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
) : Flow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable {
        val job = Job()
        val scope = CoroutineScope(dispatcher + job)

        onEach {
            block(it)
        }.launchIn(scope)

        return Closeable { job.cancel() }
    }
}

actual fun <T> Flow<T>.wrap(): CFlow<T> = CFlow(this)
