@file:Suppress("UNUSED_VARIABLE", "UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget


plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.mirego.publish)
}

group = "com.mirego"

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
        publishLibraryVariants("release", "debug")
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    js(IR) {
        nodejs()
    }

    cocoapods {
        ios.deploymentTarget = "15.0"
        pod("Reachability", "~> 3.2")
        noPodspec()
    }

    sourceSets {
        all {
            languageSettings {
                optIn("androidx.compose.material3.ExperimentalMaterial3Api")
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }

        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
            kotlin {
                explicitApi()
            }
        }

        androidMain.dependencies {
            implementation(libs.startup.runtime)
        }

        compilerOptions {
            freeCompilerArgs.add("-Xexpect-actual-classes")
        }
    }
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    namespace = "com.mirego.konnectivity"

    defaultConfig {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

ktlint {
    filter {
        exclude { element -> element.file.path.contains("generated/") }
    }
}
