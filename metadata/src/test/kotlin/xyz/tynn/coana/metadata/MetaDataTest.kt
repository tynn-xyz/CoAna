package xyz.tynn.coana.metadata

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.test.Test
import kotlin.test.assertEquals

class MetaDataTest {

    object Key : MetaData.Key<String> {
        @Suppress("UNCHECKED_CAST")
        fun <T> cast() = this as MetaData.Key<T>
    }

    val intValue = 1
    val doubleValue = 2.2
    val stringValue = "3.3.3"

    @Test
    fun `MetaData should contain key value pair`() = runBlocking {
        val metadata = MetaData(Key, stringValue)

        assertEquals(Key, metadata.key)
        assertEquals(stringValue, metadata.value)
    }

    @Test
    fun `key parameter should be contravariant to Value`() = runBlocking {
        val key = object : MetaData.Key<Number> {}

        val metadata = withContext(MetaData<Double>(key, doubleValue)) {
            coroutineContext[key]
        }

        assertEquals(MetaData<Number>(key, doubleValue), metadata)
    }

    @Test
    fun `context should contain metadata`() = runBlocking {
        val metadata = coroutineScope {
            withContext(MetaData(Key, stringValue)) {
                coroutineContext[Key]
            }
        }

        assertEquals(MetaData(Key, stringValue), metadata)
    }

    @Test
    fun `context should override metadata`() = runBlocking {
        val metadata = withContext(MetaData(Key.cast(), intValue)) {
            withContext(MetaData(Key.cast(), doubleValue)) {
                coroutineContext[Key.cast<Double>()]
            }
        }

        assertEquals(MetaData(Key.cast(), doubleValue), metadata)
    }

    @Test
    fun `context should collect metadata`() = runBlocking {
        val key2 = object : MetaData.Key<Double> {}

        val metadata = withContext(MetaData(Key.cast(), intValue)) {
            withContext(MetaData(key2, doubleValue)) {
                coroutineContext[Key.cast<Int>()] to coroutineContext[key2]
            }
        }

        assertEquals(MetaData(Key.cast(), intValue) to MetaData(key2, doubleValue), metadata)
    }

    @Test
    fun `metadata should contain latest metadata in context`() = runBlocking {
        val metadata = withContext(MetaData(Key.cast(), intValue)) {
            withContext(MetaData(Key.cast(), doubleValue)) {
                coroutineContext.metadata
            }
        }

        assertEquals(setOf(MetaData(Key.cast(), doubleValue)), metadata)
    }

    @Test
    fun `metadata should contain all metadata in context`() = runBlocking {
        val key2 = object : MetaData.Key<Double> {}
        val key3 = object : MetaData.Key<Int> {}

        val metadata = withContext(MetaData(Key, stringValue)) {
            withContext(MetaData(key2, doubleValue) + MetaData(key3, intValue)) {
                coroutineContext.metadata
            }
        }

        assertEquals(
            setOf(
                MetaData(Key, stringValue),
                MetaData(key2, doubleValue),
                MetaData(key3, intValue)
            ),
            metadata
        )
    }
}
