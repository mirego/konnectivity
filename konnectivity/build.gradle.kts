@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("maven-publish")
}

group = "com.mirego"
version = "0.1.0"

kotlin {
    android {
        publishAllLibraryVariants()
    }
    ios()
    iosSimulatorArm64()
    js(IR) {
        nodejs()
    }

    cocoapods {
        name = "Konnectivity"
        version = "${getVersion()}"
        summary = "A lightweight Kotlin Multiplatform library to monitor network state changes"
        homepage = "https://github.com/mirego/konnectivity"
        source =
            "{ :git => 'https://github.com/mirego/konnectivity.git', :tag => '${getVersion()}' }"
        ios.deploymentTarget = "14.1"

        framework {
            baseName = "Konnectivity"
            isStatic = true
        }

        pod("Reachability", "~> 3.2")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("androidx.startup:startup-runtime:1.1.1")
            }
        }
        val androidTest by getting

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by getting {
            iosSimulatorArm64Test.dependsOn(this)
        }

        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

android {
    compileSdk = 32
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}
