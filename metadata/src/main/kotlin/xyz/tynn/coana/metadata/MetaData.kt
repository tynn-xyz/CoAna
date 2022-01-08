//  Copyright 2019 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.coana.metadata

import kotlin.coroutines.CoroutineContext

/**
 * A [CoroutineContext.Element] containing a single [Value].
 *
 * @property key The key of this coroutine context metadata.
 * @property value The key of this coroutine context metadata.
 */
public data class MetaData<Value>(
    override val key: Key<in Value>,
    val value: Value,
) : CoroutineContext.Element {
    /**
     * Key for the [MetaData] of [CoroutineContext].
     */
    public interface Key<V> : CoroutineContext.Key<MetaData<V>>
}
