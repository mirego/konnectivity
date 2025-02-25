package com.mirego.konnectivity

public actual fun Konnectivity(): Konnectivity = AndroidKonnectivity(appContext)
