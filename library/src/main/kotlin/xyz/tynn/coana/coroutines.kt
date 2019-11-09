package xyz.tynn.coana

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import xyz.tynn.coana.metadata.MetaData
import kotlin.coroutines.CoroutineContext

private typealias Context = CoroutineContext

private class MutableCoana(context: CoroutineContext) {
    private val scope = context[CoanaKey.Scope]
        ?: throw IllegalStateException("CoanaKey.Scope missing")
    private val context = context[CoanaKey.Context]?.value

    val doubles = mutableMapOf<CoanaProperty<Double>, Double>()
    val longs = mutableMapOf<CoanaProperty<Long>, Long>()
    val strings = mutableMapOf<CoanaProperty<String>, String>()

    fun seal() = Coana(
        scope.value.value,
        context?.value,
        doubles.toMap(),
        longs.toMap(),
        strings.toMap()
    )
}

val CoroutineScope.coana
    get() = coroutineContext.coana

val CoroutineContext.coana
    get() = fold(MutableCoana(this)) { acc, element ->
        val key = element.key
        if (key is CoanaProperty<*>) {
            val data = element as? MetaData<*>
            @Suppress("UNCHECKED_CAST")
            when (val prop = data?.value as? CoanaValue<*>) {
                is CoanaValue.Double ->
                    acc.doubles[key as CoanaProperty<Double>] = prop.value
                is CoanaValue.Long ->
                    acc.longs[key as CoanaProperty<Long>] = prop.value
                is CoanaValue.String ->
                    acc.strings[key as CoanaProperty<String>] = prop.value
            }
        }
        acc
    }.seal()


@Suppress("FunctionName")
fun CoanaScope(
    scope: String
): Context = MetaData(
    CoanaKey.Scope,
    CoanaValue.String(scope)
)

@Suppress("FunctionName")
fun CoanaContext(
    context: String
): Context = MetaData(
    CoanaKey.Context,
    CoanaValue.String(context)
)

@Suppress("FunctionName")
fun CoanaProperty(
    key: CoanaProperty<Double>,
    value: Double
): Context = MetaData(
    key,
    CoanaValue.Double(value)
)

@Suppress("FunctionName")
fun CoanaProperty(
    key: CoanaProperty<Long>,
    value: Long
): Context = MetaData(
    key,
    CoanaValue.Long(value)
)

@Suppress("FunctionName")
fun CoanaProperty(
    key: CoanaProperty<String>,
    value: String
): Context = MetaData(
    key,
    CoanaValue.String(value)
)


suspend fun <T> withCoanaScope(
    scope: String,
    block: suspend CoroutineScope.() -> T
) = withContext(CoanaScope(scope), block)

suspend fun <T> withCoanaContext(
    context: String,
    block: suspend CoroutineScope.() -> T
) = withContext(CoanaContext(context), block)

suspend fun <T> withCoanaProperty(
    key: CoanaProperty<Double>,
    value: Double,
    block: suspend CoroutineScope.() -> T
) = withContext(CoanaProperty(key, value), block)

suspend fun <T> withCoanaProperty(
    key: CoanaProperty<Long>,
    value: Long,
    block: suspend CoroutineScope.() -> T
) = withContext(CoanaProperty(key, value), block)

suspend fun <T> withCoanaProperty(
    key: CoanaProperty<String>,
    value: String,
    block: suspend CoroutineScope.() -> T
) = withContext(CoanaProperty(key, value), block)
