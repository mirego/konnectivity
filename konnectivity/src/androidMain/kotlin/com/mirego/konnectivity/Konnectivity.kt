package com.mirego.konnectivity

actual fun Konnectivity(): Konnectivity = AndroidKonnectivity(appContext)
