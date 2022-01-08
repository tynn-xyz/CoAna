//  Copyright 2019 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:Suppress("FunctionName")

package xyz.tynn.coana

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import xyz.tynn.coana.metadata.MetaData
import kotlin.coroutines.CoroutineContext

private class MutableCoana(context: CoroutineContext) {
    private val scope = context[CoanaScopeKey]
        ?: throw IllegalStateException("CoanaScope missing")
    private val context = context[CoanaContextKey]?.value

    val doubles = mutableMapOf<CoanaPropertyKey<Double>, Double>()
    val longs = mutableMapOf<CoanaPropertyKey<Long>, Long>()
    val strings = mutableMapOf<CoanaPropertyKey<String>, String>()

    fun seal() = Coana(
        scope.value.value,
        context?.value,
        doubles.toMap(),
        longs.toMap(),
        strings.toMap(),
    )
}

/**
 * Gets the [Coana] model from a [CoroutineScope].
 */
public val CoroutineScope.coana: Coana get() = coroutineContext.coana

/**
 * Gets the [Coana] model from a [CoroutineContext].
 */
public val CoroutineContext.coana: Coana
    get() = fold(MutableCoana(this)) { acc, element ->
        acc.apply {
            val key = element.key
            if (key is CoanaPropertyKey<*>) {
                val data = element as? MetaData<*>
                @Suppress("UNCHECKED_CAST")
                when (val prop = data?.value as? CoanaValue<*>) {
                    is CoanaValue.Double ->
                        doubles[key as CoanaPropertyKey<Double>] = prop.value
                    is CoanaValue.Long ->
                        longs[key as CoanaPropertyKey<Long>] = prop.value
                    is CoanaValue.String ->
                        strings[key as CoanaPropertyKey<String>] = prop.value
                    null -> Unit
                }
            }
        }
    }.seal()

/**
 * Creates a `CoanaScope` [CoroutineContext] element with value [scope].
 */
public fun CoanaScope(
    scope: String,
): CoroutineContext = MetaData(
    CoanaScopeKey,
    CoanaValue.String(scope),
)

/**
 * Creates a `CoanaContext` [CoroutineContext] element with value [context].
 */
public fun CoanaContext(
    context: String,
): CoroutineContext = MetaData(
    CoanaContextKey,
    CoanaValue.String(context),
)

/**
 * Creates a `CoanaProperty` [CoroutineContext] element of [key] with a double [value].
 */
public fun CoanaProperty(
    key: CoanaPropertyKey<Double>,
    value: Double,
): CoroutineContext = MetaData(
    key,
    CoanaValue.Double(value),
)

/**
 * Creates a `CoanaProperty` [CoroutineContext] element of [key] with a long [value].
 */
public fun CoanaProperty(
    key: CoanaPropertyKey<Long>,
    value: Long,
): CoroutineContext = MetaData(
    key,
    CoanaValue.Long(value),
)

/**
 * Creates a `CoanaProperty` [CoroutineContext] element of [key] with a string [value].
 */
public fun CoanaProperty(
    key: CoanaPropertyKey<String>,
    value: String,
): CoroutineContext = MetaData(
    key,
    CoanaValue.String(value),
)

/**
 * Calls the [block] with [CoanaScope] with value [scope] attached to its [CoanaContext].
 */
public suspend fun <T> withCoanaScope(
    scope: String,
    block: suspend CoroutineScope.() -> T,
): T = withContext(
    CoanaScope(scope),
    block,
)

/**
 * Calls the [block] with [CoanaContext] with value [context] attached to its [CoanaContext].
 */
public suspend fun <T> withCoanaContext(
    context: String,
    block: suspend CoroutineScope.() -> T,
): T = withContext(
    CoanaContext(context),
    block,
)

/**
 * Calls the [block] with [CoanaProperty] of [key] with double [value] attached to its [CoanaContext].
 */
public suspend fun <T> withCoanaProperty(
    key: CoanaPropertyKey<Double>,
    value: Double,
    block: suspend CoroutineScope.() -> T,
): T = withContext(
    CoanaProperty(key, value),
    block,
)

/**
 * Calls the [block] with [CoanaProperty] of [key] with long [value] attached to its [CoanaContext].
 */
public suspend fun <T> withCoanaProperty(
    key: CoanaPropertyKey<Long>,
    value: Long,
    block: suspend CoroutineScope.() -> T,
): T = withContext(
    CoanaProperty(key, value),
    block,
)

/**
 * Calls the [block] with [CoanaProperty] of [key] with string [value] attached to its [CoanaContext].
 */
public suspend fun <T> withCoanaProperty(
    key: CoanaPropertyKey<String>,
    value: String,
    block: suspend CoroutineScope.() -> T,
): T = withContext(
    CoanaProperty(key, value),
    block,
)
