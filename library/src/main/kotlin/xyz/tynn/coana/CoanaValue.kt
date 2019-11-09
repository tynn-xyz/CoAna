package xyz.tynn.coana

sealed class CoanaValue<Value> {
    abstract val value: Value

    internal data class Double(
        override val value: kotlin.Double
    ) : CoanaValue<kotlin.Double>()

    internal data class Long(
        override val value: kotlin.Long
    ) : CoanaValue<kotlin.Long>()

    internal data class String(
        override val value: kotlin.String
    ) : CoanaValue<kotlin.String>()
}
