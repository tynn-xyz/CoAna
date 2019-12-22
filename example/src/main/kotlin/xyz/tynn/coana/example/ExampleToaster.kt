package xyz.tynn.coana.example

import android.content.Context
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import xyz.tynn.coana.coana
import xyz.tynn.coana.example.BuildConfig.VERSION_NAME
import xyz.tynn.coana.example.ExamplePropertyKey.AppVersion
import xyz.tynn.coana.example.ExamplePropertyKey.SubmitCount
import xyz.tynn.coana.withCoanaProperty
import xyz.tynn.coana.withCoanaScope
import java.util.concurrent.atomic.AtomicLong
import kotlin.coroutines.CoroutineContext

internal class ExampleToaster(
    private val context: Context
) {

    private val count = AtomicLong()

    fun prepareToast(
        block: suspend CoroutineScope.() -> Unit
    ) = GlobalScope.launch {
        withCoanaScope("Coana") {
            withCoanaProperty(SubmitCount, count.incrementAndGet()) {
                block()
            }
        }
    }

    suspend fun makeToast(
        coroutineContext: CoroutineContext
    ) = coroutineScope {
        withContext(Main + coroutineContext) {
            withCoanaProperty(AppVersion, VERSION_NAME) {
                makeText(context, coana.toString(), LENGTH_LONG).show()
            }
        }
    }
}
