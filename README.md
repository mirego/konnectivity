<div align="center">
  <img src="https://user-images.githubusercontent.com/5982196/205750835-4f657a00-1bca-4631-96d9-cc4acbfbd2eb.png" width="600" />
  <p><br />A lightweight Kotlin Multiplatform library to monitor network state changes</p>
  <a href="https://github.com/mirego/kmp-boilerplate/actions/workflows/ci.yml"><img src="https://github.com/mirego/kmp-boilerplate/actions/workflows/ci.yaml/badge.svg"/></a>
  <a href="https://kotlinlang.org/"><img src="https://img.shields.io/badge/kotlin-1.8.10-blue.svg?logo=kotlin"/></a>
  <a href="https://opensource.org/licenses/BSD-3-Clause"><img src="https://img.shields.io/badge/License-BSD_3--Clause-blue.svg"/></a>
</div>

## Installation

### Gradle

Add the dependency to your common source-set dependencies:
```kotlin
sourceSets {
    commonMain {
        dependencies {
             api("com.mirego:konnectivity:0.1.0")
        }
    }
}
```

Make sure you have [Mirego](https://open.mirego.com/)'s public Maven in your list of repositories:
```kotlin
repositories {
    maven("https://s3.amazonaws.com/mirego-maven/public")
}
```

Using the [Kotlin CocoaPods Plugin](https://kotlinlang.org/docs/native-cocoapods.html), add the [Reachability](https://cocoapods.org/pods/Reachability) pod dependency:
```kotlin
kotlin {
    cocoapods {
        pod("Reachability", "~> 3.2")
    }
} 
```

## Usage

```kotlin
import com.mirego.konnectivity.Konnectivity
import com.mirego.konnectivity.NetworkState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

val konnectivity = Konnectivity()

konnectivity.networkState
    .onEach { networkState ->
        when (networkState) {
            is NetworkState.Reachable -> when {
                networkState.metered -> println("You're online, but your connection is metered.")
                else -> println("You're online!")
            }
            NetworkState.Unreachable -> println("You're offline.")
        }
    }
    .launchIn(MainScope())
```


## License

Konnectivity is © 2023 [Mirego](https://www.mirego.com) and may be freely distributed under
the [New BSD license](http://opensource.org/licenses/BSD-3-Clause). See
the [`LICENSE.md`](https://github.com/mirego/konnectivity/blob/main/LICENSE.md) file.

## About Mirego

[Mirego](https://www.mirego.com) is a team of passionate people who believe that work is a place
where you can innovate and have fun. We’re a team of [talented people](https://life.mirego.com) who
imagine and build beautiful Web and mobile applications. We come together to share ideas
and [change the world](http://www.mirego.org).

We also [love open-source software](https://open.mirego.com) and we try to give back to the
community as much as we can.
