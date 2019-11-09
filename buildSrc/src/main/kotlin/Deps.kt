@file:Suppress("SpellCheckingInspection")

object Deps {
    val android = Android
    val kotlin = Kotlin
    val kotlinx = Kotlinx

    object Android {
        const val plugin = "${Groups.android}:gradle:${Versions.android}"
    }

    object Kotlin {
        const val plugin = "${Groups.kotlin}:kotlin-gradle-plugin:${Versions.kotlin}"
        const val stdlib = "${Groups.kotlin}:kotlin-stdlib-jdk7:${Versions.kotlin}"
        val test = Test

        object Test {
            const val junit = "${Groups.kotlin}:kotlin-test-junit:${Versions.kotlin}"
        }
    }

    object Kotlinx {
        val coroutines = Coroutines

        object Coroutines {
            const val core = "${Groups.kotlinx}:kotlinx-coroutines-core:${Versions.kotlinx}"
        }
    }
}
