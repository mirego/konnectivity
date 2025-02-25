@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://s3.amazonaws.com/mirego-maven/public")
    }

    resolutionStrategy {
        eachPlugin {
            when (val namespace = requested.id.namespace) {
                "mirego" -> useModule("$namespace:${requested.id.name}-plugin:${requested.version}")
            }
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "konnectivity"

val samplesEnabled = !extra.has("disable_samples")

include(":konnectivity")

if (samplesEnabled) {
    include(":sample")
    include(":sample:shared")
    include(":sample:androidApp")
}