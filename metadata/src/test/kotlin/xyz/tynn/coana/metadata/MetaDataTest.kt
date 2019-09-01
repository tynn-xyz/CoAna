package xyz.tynn.coana.metadata

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.test.Test
import kotlin.test.assertEquals

class MetaDataTest {

    val key = object : MetaData.Key {}

    val intValue = MetaData.Value.Int(1)
    val doubleValue = MetaData.Value.Double(2.2)
    val stringValue = MetaData.Value.String("3.3.3")

    @Test
    fun `MetaData should contain key value pair`() = runBlocking {
        val metadata = MetaData(key, stringValue)

        assertEquals(key, metadata.key)
        assertEquals(stringValue, metadata.value)
    }

    @Test
    fun `MetaData Value Double should contain value`() = runBlocking {
        assertEquals(2.2, doubleValue.value)
    }

    @Test
    fun `MetaData Value Int should contain value`() = runBlocking {
        assertEquals(1, intValue.value)
    }

    @Test
    fun `MetaData Value String should contain value`() = runBlocking {
        assertEquals("3.3.3", stringValue.value)
    }

    @Test
    fun `context should contain metadata`() = runBlocking {
        val metadata = withContext(MetaData(key, stringValue)) {
            coroutineContext[key]
        }

        assertEquals(MetaData(key, stringValue), metadata)
    }

    @Test
    fun `context should override metadata`() = runBlocking {
        val metadata = withContext(MetaData(key, intValue)) {
            withContext(MetaData(key, doubleValue)) {
                coroutineContext[key]
            }
        }

        assertEquals(MetaData(key, doubleValue), metadata)
    }

    @Test
    fun `context should collect metadata`() = runBlocking {
        val key2 = object : MetaData.Key {}

        val metadata = withContext(MetaData(key, intValue)) {
            withContext(MetaData(key2, doubleValue)) {
                coroutineContext[key] to coroutineContext[key2]
            }
        }

        assertEquals(MetaData(key, intValue) to MetaData(key2, doubleValue), metadata)
    }

    @Test
    fun `metadata should contain latest metadata in context`() = runBlocking {
        val metadata = withContext(MetaData(key, intValue)) {
            withContext(MetaData(key, doubleValue)) {
                coroutineContext.metadata
            }
        }

        assertEquals(setOf(MetaData(key, doubleValue)), metadata)
    }

    @Test
    fun `metadata should contain all metadata in context`() = runBlocking {
        val key2 = object : MetaData.Key {}
        val key3 = object : MetaData.Key {}

        val metadata = withContext(MetaData(key, stringValue)) {
            withContext(MetaData(key2, doubleValue) + MetaData(key3, intValue)) {
                coroutineContext.metadata
            }
        }

        assertEquals(
            setOf(
                MetaData(key, stringValue),
                MetaData(key2, doubleValue),
                MetaData(key3, intValue)
            ),
            metadata
        )
    }
}
