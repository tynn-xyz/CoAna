package xyz.tynn.coana.test

import xyz.tynn.coana.CoanaProperty

sealed class TestProperty<V> : CoanaProperty<V> {
    object Double : TestProperty<kotlin.Double>()
    object Long : TestProperty<kotlin.Long>()
    object String : TestProperty<kotlin.String>()
}
