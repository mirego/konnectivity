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
    ios()
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
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.startup.runtime)
            }
        }
        val androidUnitTest by getting

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
