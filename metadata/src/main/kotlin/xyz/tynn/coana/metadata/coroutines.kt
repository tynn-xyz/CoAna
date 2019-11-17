//  Copyright 2019 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.coana.metadata

import kotlin.coroutines.CoroutineContext

/**
 * Contains all the [MetaData] of the [CoroutineContext].
 */
val CoroutineContext.metadata
    get() = fold(mutableSetOf<MetaData<*>>()) { acc, element ->
        acc.apply { (element as? MetaData<*>)?.let(::add) }
    }.toSet()

