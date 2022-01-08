//  Copyright 2019 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

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

internal class ExampleToaster {

    private val count = AtomicLong()

    private val job = SupervisorJob()
    private val scope = CoroutineScope(job)

    fun destroy() = job.cancel()

    fun prepareToast(
        block: suspend CoroutineScope.() -> Unit,
    ) = scope.launch {
        withCoanaScope("Coana") {
            withCoanaProperty(SubmitCount, count.incrementAndGet()) {
                block()
            }
        }
    }

    suspend fun Context.makeToast(
        coroutineContext: CoroutineContext,
    ) = coroutineScope {
        withContext(Main + coroutineContext) {
            withCoanaProperty(AppVersion, VERSION_NAME) {
                makeText(applicationContext, coana.toString(), LENGTH_LONG).show()
            }
        }
    }
}
