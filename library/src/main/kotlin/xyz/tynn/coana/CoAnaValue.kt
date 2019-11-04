package xyz.tynn.coana

sealed class CoAnaValue<Value> {
    abstract val value: Value

    internal data class Double(
        override val value: kotlin.Double
    ) : CoAnaValue<kotlin.Double>()

    internal data class Long(
        override val value: kotlin.Long
    ) : CoAnaValue<kotlin.Long>()

    internal data class String(
        override val value: kotlin.String
    ) : CoAnaValue<kotlin.String>()
}
