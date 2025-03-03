package com.mirego.konnectivity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.shareIn

public fun Konnectivity.shared(
    coroutineScope: CoroutineScope,
    shareStarted: SharingStarted = SharingStarted.WhileSubscribed(),
    replay: Int = 1,
): Konnectivity =
    SharedKonnectivity(
        this,
        coroutineScope,
        shareStarted,
        replay,
    )

private class SharedKonnectivity(
    konnectivity: Konnectivity,
    coroutineScope: CoroutineScope,
    shareStarted: SharingStarted,
    replay: Int,
) : Konnectivity {
    override val networkState = konnectivity.networkState
        .distinctUntilChanged()
        .shareIn(coroutineScope, shareStarted, replay)
}
