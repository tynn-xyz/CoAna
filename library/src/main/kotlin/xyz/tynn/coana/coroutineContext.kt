package xyz.tynn.coana

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import xyz.tynn.coana.metadata.MetaData
import kotlin.coroutines.CoroutineContext

private typealias Context = CoroutineContext

private class MutableCoAna(context: CoroutineContext) {
    private val scope = context[CoAnaKey.Scope]
        ?: throw IllegalStateException("CoAnaKey.Scope missing")
    private val context = context[CoAnaKey.Context]?.value

    val doubles = mutableMapOf<CoAnaProperty<Double>, Double>()
    val longs = mutableMapOf<CoAnaProperty<Long>, Long>()
    val strings = mutableMapOf<CoAnaProperty<String>, String>()

    fun seal() = CoAna(
        scope.value.value,
        context?.value,
        doubles.toMap(),
        longs.toMap(),
        strings.toMap()
    )
}

val CoroutineScope.coAna
    get() = coroutineContext.coAna

val CoroutineContext.coAna
    get() = fold(MutableCoAna(this)) { acc, element ->
        val key = element.key
        if (key is CoAnaProperty<*>) {
            val data = element as? MetaData<*>
            @Suppress("UNCHECKED_CAST")
            when (val prop = data?.value as? CoAnaValue<*>) {
                is CoAnaValue.Double ->
                    acc.doubles[key as CoAnaProperty<Double>] = prop.value
                is CoAnaValue.Long ->
                    acc.longs[key as CoAnaProperty<Long>] = prop.value
                is CoAnaValue.String ->
                    acc.strings[key as CoAnaProperty<String>] = prop.value
            }
        }
        acc
    }.seal()


@Suppress("FunctionName")
fun CoAnaScope(
    scope: String
): Context = MetaData(
    CoAnaKey.Scope,
    CoAnaValue.String(scope)
)

@Suppress("FunctionName")
fun CoAnaContext(
    context: String
): Context = MetaData(
    CoAnaKey.Context,
    CoAnaValue.String(context)
)

@Suppress("FunctionName")
fun CoAnaProperty(
    key: CoAnaProperty<Double>,
    value: Double
): Context = MetaData(
    key,
    CoAnaValue.Double(value)
)

@Suppress("FunctionName")
fun CoAnaProperty(
    key: CoAnaProperty<Long>,
    value: Long
): Context = MetaData(
    key,
    CoAnaValue.Long(value)
)

@Suppress("FunctionName")
fun CoAnaProperty(
    key: CoAnaProperty<String>,
    value: String
): Context = MetaData(
    key,
    CoAnaValue.String(value)
)


suspend fun <T> withCoAnaScope(
    scope: String,
    block: suspend CoroutineScope.() -> T
) = withContext(CoAnaScope(scope), block)

suspend fun <T> withCoAnaContext(
    context: String,
    block: suspend CoroutineScope.() -> T
) = withContext(CoAnaContext(context), block)

suspend fun <T> withCoAnaProperty(
    key: CoAnaProperty<Double>,
    value: Double,
    block: suspend CoroutineScope.() -> T
) = withContext(CoAnaProperty(key, value), block)

suspend fun <T> withCoAnaProperty(
    key: CoAnaProperty<Long>,
    value: Long,
    block: suspend CoroutineScope.() -> T
) = withContext(CoAnaProperty(key, value), block)

suspend fun <T> withCoAnaProperty(
    key: CoAnaProperty<String>,
    value: String,
    block: suspend CoroutineScope.() -> T
) = withContext(CoAnaProperty(key, value), block)
