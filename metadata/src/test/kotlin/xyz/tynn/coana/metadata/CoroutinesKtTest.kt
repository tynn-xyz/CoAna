//  Copyright 2019 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.coana.metadata

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CoroutinesKtTest {

    @Test
    fun `MetaData should contain key value pair`() = runBlocking {
        val metadata = coroutineScope {
            withContext(MetaData(TestKey, stringValue)) {
                coroutineContext[TestKey]
            }
        }

        assertEquals(TestKey.cast(), metadata?.key)
        assertEquals(stringValue, metadata?.value)
    }

    @Test
    fun `key parameter should be contravariant to Value`() = runBlocking {
        val key = object : MetaData.Key<Number> {}

        @Suppress("RemoveExplicitTypeArguments")
        val metadata = withContext(MetaData<Double>(key, doubleValue)) {
            coroutineContext[key]
        }

        assertEquals(MetaData<Number>(key, doubleValue), metadata)
    }

    @Test
    fun `context should contain metadata`() = runBlocking {
        val metadata = coroutineScope {
            withContext(MetaData(TestKey, stringValue)) {
                coroutineContext[TestKey]
            }
        }

        assertEquals(MetaData(TestKey, stringValue), metadata)
    }

    @Test
    fun `context should override metadata`() = runBlocking {
        val metadata = withContext(MetaData(TestKey.cast(), intValue)) {
            withContext(MetaData(TestKey.cast(), doubleValue)) {
                coroutineContext[TestKey.cast<Double>()]
            }
        }

        assertEquals(MetaData(TestKey.cast(), doubleValue), metadata)
    }

    @Test
    fun `context should collect metadata`() = runBlocking {
        val key2 = object : MetaData.Key<Double> {}

        val metadata = withContext(MetaData(TestKey.cast(), intValue)) {
            withContext(MetaData(key2, doubleValue)) {
                coroutineContext[TestKey.cast<Int>()] to coroutineContext[key2]
            }
        }

        assertEquals(MetaData(TestKey.cast(), intValue) to MetaData(key2, doubleValue), metadata)
    }

    @Test
    fun `metadata should contain latest metadata in context`() = runBlocking {
        val metadata = withContext(MetaData(TestKey.cast(), intValue)) {
            withContext(MetaData(TestKey.cast(), doubleValue)) {
                coroutineContext.metadata
            }
        }

        assertEquals(setOf(MetaData(TestKey.cast(), doubleValue)), metadata)
    }

    @Test
    fun `metadata should contain all metadata in context`() = runBlocking {
        val key2 = object : MetaData.Key<Double> {}
        val key3 = object : MetaData.Key<Int> {}

        val metadata = withContext(MetaData(TestKey, stringValue)) {
            withContext(MetaData(key2, doubleValue) + MetaData(key3, intValue)) {
                coroutineContext.metadata
            }
        }

        assertEquals(
            setOf(
                MetaData(TestKey, stringValue),
                MetaData(key2, doubleValue),
                MetaData(key3, intValue)
            ),
            metadata
        )
    }
}
