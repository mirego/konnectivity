plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.native.cocoapods)
    alias(libs.plugins.android.library)
    id("maven-publish")
    id("mirego.publish") version "1.0"
    id("mirego.release") version "2.0"
}
group = "com.mirego"

kotlin {
    jvmToolchain(17)
    androidTarget {
        publishAllLibraryVariants()
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    js(IR) {
        nodejs()
    }

    cocoapods {
        ios.deploymentTarget = "14.1"
        pod("Reachability", "~> 3.2")
        noPodspec()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.startup.runtime)
            }
        }

        jsTest {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    namespace = "com.mirego.konnectivity"

    defaultConfig {
        compileSdk = 34
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

release {
    checkTasks = listOf("check")
    buildTasks = listOf("publish")
}
