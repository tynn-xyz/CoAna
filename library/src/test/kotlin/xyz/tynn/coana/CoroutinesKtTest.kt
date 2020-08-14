//  Copyright 2019 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.coana

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext.Element
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CoroutinesKtTest {

    val scope = "scope"
    val context = "context"
    val long = 1L
    val double = 2.2
    val string = "3.3.3"

    @Test
    fun `coana should fail without a scope`() = runBlocking {
        assertFailsWith<IllegalStateException> {
            coana
        }
        Unit
    }

    @Test
    fun `coana should have a scope`() = runBlocking {
        withContext(CoanaScope(scope)) {
            assertEquals(scope, coana.scope)
        }
    }

    @Test
    fun `coana should have a context when added`() = runBlocking {
        withCoanaScope(scope) {
            withContext(CoanaContext(context)) {
                assertEquals(context, coana.context)
            }
        }
    }

    @Test
    fun `coana should have a double property when added`() = runBlocking {
        withCoanaScope(scope) {
            withContext(CoanaProperty(TestPropertyKey.Double, double)) {
                assertEquals(double, coana.doubleProperties[TestPropertyKey.Double])
            }
        }
    }

    @Test
    fun `coana should have a long property when added`() = runBlocking {
        withCoanaScope(scope) {
            withContext(CoanaProperty(TestPropertyKey.Long, long)) {
                assertEquals(long, coana.longProperties[TestPropertyKey.Long])
            }
        }
    }

    @Test
    fun `coana should have a string property when added`() = runBlocking {
        withCoanaScope(scope) {
            withContext(CoanaProperty(TestPropertyKey.String, string)) {
                assertEquals(string, coana.stringProperties[TestPropertyKey.String])
            }
        }
    }

    @Test
    fun `coana should skip CoanaContextProperty without MetaData`() = runBlocking {
        withCoanaScope(scope) {
            withContext(object : Element {
                override val key get() = TestPropertyKey.String
            }) {
                assertEquals(emptyMap(), coana.stringProperties)
            }
        }
    }

    @Test
    fun `coana should skip CoanaProperty without MetaData`() = runBlocking {
        withCoanaScope(scope) {
            withContext(object : Element {
                override val key get() = TestPropertyKey.String
            }) {
                assertEquals(emptyMap(), coana.stringProperties)
            }
        }
    }

    @Test
    fun `withCoanaScope should add a CoanaScope`() = runBlocking {
        val value = withCoanaScope(scope) {
            coroutineContext[CoanaScopeKey]?.value
        }

        assertEquals(CoanaValue.String(scope), value)
    }

    @Test
    fun `withCoanaContext should add a CoanaContext`() = runBlocking {
        val value = withCoanaContext(context) {
            coroutineContext[CoanaContextKey]?.value
        }

        assertEquals(CoanaValue.String(context), value)
    }

    @Test
    fun `withCoanaProperty should add a Double CoanaProperty`() = runBlocking {
        val value = withCoanaProperty(TestPropertyKey.Double, double) {
            coroutineContext[TestPropertyKey.Double]?.value
        }

        assertEquals(CoanaValue.Double(double), value)
    }

    @Test
    fun `withCoanaProperty should add a Long CoanaProperty`() = runBlocking {
        val value = withCoanaProperty(TestPropertyKey.Long, long) {
            coroutineContext[TestPropertyKey.Long]?.value
        }

        assertEquals(CoanaValue.Long(long), value)
    }

    @Test
    fun `withCoanaProperty should add a String CoanaProperty`() = runBlocking {
        val value = withCoanaProperty(TestPropertyKey.String, string) {
            coroutineContext[TestPropertyKey.String]?.value
        }

        assertEquals(CoanaValue.String(string), value)
    }
}
