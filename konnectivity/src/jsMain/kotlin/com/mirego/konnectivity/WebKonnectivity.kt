package com.mirego.konnectivity

import kotlinx.browser.window
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.w3c.dom.events.Event

public class WebKonnectivity : Konnectivity {

    override val networkState: Flow<NetworkState> = callbackFlow {
        val currentNetworkState = getCurrentNetworkState()
        trySend(currentNetworkState)

        val callback: (event: Event) -> Unit = { _ ->
            val networkState = getCurrentNetworkState()
            trySend(networkState)
        }

        window.addEventListener("online", callback)
        window.addEventListener("offline", callback)

        awaitClose {
            window.removeEventListener("offline", callback)
            window.removeEventListener("online", callback)
        }
    }

    private fun getCurrentNetworkState() = when {
        window.navigator.onLine -> NetworkState.Reachable(metered = null)
        else -> NetworkState.Unreachable
    }
}
