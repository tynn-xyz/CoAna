package xyz.tynn.coana

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import xyz.tynn.coana.test.TestProperty
import kotlin.coroutines.CoroutineContext.Element
import kotlin.test.Test
import kotlin.test.assertEquals

class CoroutineContextKtTest {

    val scope = "scope"
    val context = "context"
    val long = 1L
    val double = 2.2
    val string = "3.3.3"

    @Test(expected = IllegalStateException::class)
    fun `coAna should fail without a scope`() {
        runBlocking {
            coAna
        }
    }

    @Test
    fun `coAna should have a scope`() = runBlocking {
        withContext(CoAnaScope(scope)) {
            assertEquals(scope, coAna.scope)
        }
    }

    @Test
    fun `coAna should have a context when added`() = runBlocking {
        withCoAnaScope(scope) {
            withContext(CoAnaContext(context)) {
                assertEquals(context, coAna.context)
            }
        }
    }

    @Test
    fun `coAna should have a double property when added`() = runBlocking {
        withCoAnaScope(scope) {
            withContext(CoAnaProperty(TestProperty.Double, double)) {
                assertEquals(double, coAna.doubleProperties[TestProperty.Double])
            }
        }
    }

    @Test
    fun `coAna should have a long property when added`() = runBlocking {
        withCoAnaScope(scope) {
            withContext(CoAnaProperty(TestProperty.Long, long)) {
                assertEquals(long, coAna.longProperties[TestProperty.Long])
            }
        }
    }

    @Test
    fun `coAna should have a string property when added`() = runBlocking {
        withCoAnaScope(scope) {
            withContext(CoAnaProperty(TestProperty.String, string)) {
                assertEquals(string, coAna.stringProperties[TestProperty.String])
            }
        }
    }

    fun `coAna should fail with a context without MetaData`() = runBlocking {
        withCoAnaScope(scope) {
            withCoAnaContext(context) {

            }
        }
    }

    @Test
    fun `coAna should skip CoAnaContextProperty without MetaData`() = runBlocking {
        withCoAnaScope(scope) {
            withContext(object : Element {
                override val key get() = TestProperty.String
            }) {
                assertEquals(emptyMap(), coAna.stringProperties)
            }
        }
    }

    @Test
    fun `coAna should skip CoAnaProperty without MetaData`() = runBlocking {
        withCoAnaScope(scope) {
            withContext(object : Element {
                override val key get() = TestProperty.String
            }) {
                assertEquals(emptyMap(), coAna.stringProperties)
            }
        }
    }

    @Test
    fun `withCoAnaScope should add a CoAnaScope`() = runBlocking {
        val value = withCoAnaScope(scope) {
            coroutineContext[CoAnaKey.Scope]?.value
        }

        assertEquals(CoAnaValue.String(scope), value)
    }

    @Test
    fun `withCoAnaContext should add a CoAnaContext`() = runBlocking {
        val value = withCoAnaContext(context) {
            coroutineContext[CoAnaKey.Context]?.value
        }

        assertEquals(CoAnaValue.String(context), value)
    }

    @Test
    fun `withCoAnaProperty should add a Double CoAnaProperty`() = runBlocking {
        val value = withCoAnaProperty(TestProperty.Double, double) {
            coroutineContext[TestProperty.Double]?.value
        }

        assertEquals(CoAnaValue.Double(double), value)
    }

    @Test
    fun `withCoAnaProperty should add a Long CoAnaProperty`() = runBlocking {
        val value = withCoAnaProperty(TestProperty.Long, long) {
            coroutineContext[TestProperty.Long]?.value
        }

        assertEquals(CoAnaValue.Long(long), value)
    }

    @Test
    fun `withCoAnaProperty should add a String CoAnaProperty`() = runBlocking {
        val value = withCoAnaProperty(TestProperty.String, string) {
            coroutineContext[TestProperty.String]?.value
        }

        assertEquals(CoAnaValue.String(string), value)
    }
}
