//  Copyright 2019 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.coana

/**
 * The [Coana] model representing the scope, an optional context
 * and all properties of the coroutine context.
 *
 * @property scope The [CoanaScope] of the coroutine context.
 * @property context The [CoanaContext] of the coroutine context.
 * @property doubleProperties The double properties of the coroutine context.
 * @property longProperties The long properties of the coroutine context.
 * @property stringProperties The string properties of the coroutine context.
 */
public data class Coana internal constructor(
    val scope: String,
    val context: String?,
    val doubleProperties: Map<CoanaPropertyKey<Double>, Double>,
    val longProperties: Map<CoanaPropertyKey<Long>, Long>,
    val stringProperties: Map<CoanaPropertyKey<String>, String>,
)
