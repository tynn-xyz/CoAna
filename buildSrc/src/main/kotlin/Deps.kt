object Deps {
    val android = object {
        val plugin = "${Groups.android}:gradle:${Versions.android}"
    }
    val kotlin = object {
        val plugin = "${Groups.kotlin}:kotlin-gradle-plugin:${Versions.kotlin}"
        val stdlib = "${Groups.kotlin}:kotlin-stdlib-jdk7:${Versions.kotlin}"
    }
}
