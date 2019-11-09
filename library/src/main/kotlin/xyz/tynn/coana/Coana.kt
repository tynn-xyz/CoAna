package xyz.tynn.coana

data class Coana internal constructor(
    val scope: String,
    val context: String?,
    val doubleProperties: Map<CoanaProperty<Double>, Double>,
    val longProperties: Map<CoanaProperty<Long>, Long>,
    val stringProperties: Map<CoanaProperty<String>, String>
)
