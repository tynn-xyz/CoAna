package xyz.tynn.coana.metadata

import kotlin.coroutines.CoroutineContext

data class MetaData<out T>(
    override val key: Key,
    val value: Value<T>
) : CoroutineContext.Element {

    interface Key : CoroutineContext.Key<MetaData<*>>

    sealed class Value<out T> {
        abstract val value: T

        data class Double(
            override val value: kotlin.Double
        ) : Value<kotlin.Double>()

        data class Int(
            override val value: kotlin.Int
        ) : Value<kotlin.Int>()

        data class String(
            override val value: kotlin.String
        ) : Value<kotlin.String>()
    }
}

val CoroutineContext.metadata
    get() = fold(mutableSetOf<MetaData<*>>()) { set, element ->
        set.apply { (element as? MetaData<*>)?.let(::add) }
    }.toSet()
