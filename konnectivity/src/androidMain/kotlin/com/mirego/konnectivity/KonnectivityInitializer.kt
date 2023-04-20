package com.mirego.konnectivity

import android.content.Context
import androidx.startup.Initializer

internal lateinit var appContext: Context

class KonnectivityInitializer : Initializer<Context> {
    override fun create(context: Context) = context.also {
        appContext = it.applicationContext
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
