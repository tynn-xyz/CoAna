package xyz.tynn.coana.test

import xyz.tynn.coana.CoAnaProperty

sealed class TestProperty<V> : CoAnaProperty<V> {
    object Double : TestProperty<kotlin.Double>()
    object Long : TestProperty<kotlin.Long>()
    object String : TestProperty<kotlin.String>()
}
