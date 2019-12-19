@Suppress("SpellCheckingInspection")
object Deps {
    val kotlin = Kotlin
    val kotlinx = Kotlinx

    object Kotlin {
        private const val group = "org.jetbrains.kotlin"

        const val stdlib = "$group:kotlin-stdlib"
        val test = Test

        object Test {
            const val junit = "org.jetbrains.kotlin:kotlin-test-junit"
        }
    }

    object Kotlinx {
        private const val group = "org.jetbrains.kotlinx"

        val coroutines = Coroutines

        object Coroutines {
            private const val version = "1.3.0"

            const val core = "$group:kotlinx-coroutines-core:$version"
        }
    }
}
