package xyz.tynn.coana.metadata

import kotlin.coroutines.CoroutineContext

data class MetaData<Value>(
    override val key: Key<in Value>,
    val value: Value
) : CoroutineContext.Element {
    interface Key<V> : CoroutineContext.Key<MetaData<V>>
}

val CoroutineContext.metadata
    get() = fold(mutableSetOf<MetaData<*>>()) { acc, element ->
        acc.apply { (element as? MetaData<*>)?.let(::add) }
    }.toSet()

