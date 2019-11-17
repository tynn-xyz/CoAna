//  Copyright 2019 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.coana

/**
 * Supported values for [Coana] scope, context and properties.
 *
 * This type serves as a generic marker for [CoanaPropertyKey].
 */
sealed class CoanaValue<Value> {
    internal abstract val value: Value

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
