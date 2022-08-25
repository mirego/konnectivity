package com.mirego.konnectivity

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}