package com.mirego.konnectivity

import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
actual fun Konnectivity(): Konnectivity = IosKonnectivity()
