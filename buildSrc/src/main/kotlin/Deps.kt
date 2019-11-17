@file:Suppress("SpellCheckingInspection")

object Deps {
    val android = Android
    val kotlin = Kotlin
    val kotlinx = Kotlinx

    object Android {
        const val plugin = "${Groups.android}:gradle:${Versions.android}"
    }

    object Kotlin {
        private const val group = Groups.kotlin

        const val stdlib = "$group:kotlin-stdlib"
        val test = Test

        object Test {
            const val junit = "${Groups.kotlin}:kotlin-test-junit"
        }
    }

    object Kotlinx {
        private const val group = Groups.kotlinx
        private const val version = Versions.kotlinx

        val coroutines = Coroutines

        object Coroutines {
            const val core = "$group:kotlinx-coroutines-core:$version"
        }
    }
}
