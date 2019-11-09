package xyz.tynn.coana

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import xyz.tynn.coana.test.TestProperty
import kotlin.coroutines.CoroutineContext.Element
import kotlin.test.Test
import kotlin.test.assertEquals

class CoroutinesKtTest {

    val scope = "scope"
    val context = "context"
    val long = 1L
    val double = 2.2
    val string = "3.3.3"

    @Test(expected = IllegalStateException::class)
    fun `coana should fail without a scope`() {
        runBlocking {
            coana
        }
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
            withContext(CoanaProperty(TestProperty.Double, double)) {
                assertEquals(double, coana.doubleProperties[TestProperty.Double])
            }
        }
    }

    @Test
    fun `coana should have a long property when added`() = runBlocking {
        withCoanaScope(scope) {
            withContext(CoanaProperty(TestProperty.Long, long)) {
                assertEquals(long, coana.longProperties[TestProperty.Long])
            }
        }
    }

    @Test
    fun `coana should have a string property when added`() = runBlocking {
        withCoanaScope(scope) {
            withContext(CoanaProperty(TestProperty.String, string)) {
                assertEquals(string, coana.stringProperties[TestProperty.String])
            }
        }
    }

    @Test
    fun `coana should skip CoanaContextProperty without MetaData`() = runBlocking {
        withCoanaScope(scope) {
            withContext(object : Element {
                override val key get() = TestProperty.String
            }) {
                assertEquals(emptyMap(), coana.stringProperties)
            }
        }
    }

    @Test
    fun `coana should skip CoanaProperty without MetaData`() = runBlocking {
        withCoanaScope(scope) {
            withContext(object : Element {
                override val key get() = TestProperty.String
            }) {
                assertEquals(emptyMap(), coana.stringProperties)
            }
        }
    }

    @Test
    fun `withCoanaScope should add a CoanaScope`() = runBlocking {
        val value = withCoanaScope(scope) {
            coroutineContext[CoanaKey.Scope]?.value
        }

        assertEquals(CoanaValue.String(scope), value)
    }

    @Test
    fun `withCoanaContext should add a CoanaContext`() = runBlocking {
        val value = withCoanaContext(context) {
            coroutineContext[CoanaKey.Context]?.value
        }

        assertEquals(CoanaValue.String(context), value)
    }

    @Test
    fun `withCoanaProperty should add a Double CoanaProperty`() = runBlocking {
        val value = withCoanaProperty(TestProperty.Double, double) {
            coroutineContext[TestProperty.Double]?.value
        }

        assertEquals(CoanaValue.Double(double), value)
    }

    @Test
    fun `withCoanaProperty should add a Long CoanaProperty`() = runBlocking {
        val value = withCoanaProperty(TestProperty.Long, long) {
            coroutineContext[TestProperty.Long]?.value
        }

        assertEquals(CoanaValue.Long(long), value)
    }

    @Test
    fun `withCoanaProperty should add a String CoanaProperty`() = runBlocking {
        val value = withCoanaProperty(TestProperty.String, string) {
            coroutineContext[TestProperty.String]?.value
        }

        assertEquals(CoanaValue.String(string), value)
    }
}
