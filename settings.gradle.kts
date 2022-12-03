pluginManagement {
    resolutionStrategy {
        repositories {
            google()
            gradlePluginPortal()
            mavenCentral()
            maven("https://s3.amazonaws.com/mirego-maven/public")
        }

        eachPlugin {
            when (val namespace = requested.id.namespace) {
                "mirego" -> useModule("$namespace:${requested.id.name}-plugin:${requested.version}")
            }
        }
    }
}

rootProject.name = "Konnectivity"
include(":konnectivity")
