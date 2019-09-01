object Deps {
    val android = object {
        val plugin = "${Groups.android}:gradle:${Versions.android}"
    }
    val kotlin = object {
        val plugin = "${Groups.kotlin}:kotlin-gradle-plugin:${Versions.kotlin}"
        val stdlib = "${Groups.kotlin}:kotlin-stdlib-jdk7:${Versions.kotlin}"
        val test = object {
            val junit = "${Groups.kotlin}:kotlin-test-junit:${Versions.kotlin}"
        }
    }
    val kotlinx = object {
        val coroutines = object {
            val core = "${Groups.kotlinx}:kotlinx-coroutines-core:${Versions.kotlinx}"
        }
    }
}
