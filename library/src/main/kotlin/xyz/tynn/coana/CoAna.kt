package xyz.tynn.coana

data class CoAna internal constructor(
    val scope: String,
    val context: String?,
    val doubleProperties: Map<CoAnaProperty<Double>, Double>,
    val longProperties: Map<CoAnaProperty<Long>, Long>,
    val stringProperties: Map<CoAnaProperty<String>, String>
)
