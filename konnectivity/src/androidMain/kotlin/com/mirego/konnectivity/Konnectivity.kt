package com.mirego.konnectivity

import android.content.Context
import androidx.startup.Initializer

private lateinit var appContext: Context

actual fun Konnectivity(): Konnectivity = AndroidKonnectivity(appContext)

internal class KonnectivityInitializer : Initializer<Context> {

    override fun create(context: Context): Context = context.applicationContext.also {
        appContext = it
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
