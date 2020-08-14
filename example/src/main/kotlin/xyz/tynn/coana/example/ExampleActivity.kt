//  Copyright 2019 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.coana.example

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_example.*
import xyz.tynn.coana.CoanaProperty
import xyz.tynn.coana.example.ExamplePropertyKey.SubmitNote
import xyz.tynn.coana.example.ExamplePropertyKey.SubmitProgress
import xyz.tynn.coana.withCoanaContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class ExampleActivity : Activity() {

    private val toaster by lazy { ExampleToaster(applicationContext) }

    private var properties: CoroutineContext = EmptyCoroutineContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        bindButtons()
        bindInputs()
    }

    private fun bindButtons() = with(toaster) {
        submit.setOnClickListener {
            prepareToast {
                makeToast(properties)
            }
        }

        submit_context.setOnClickListener {
            prepareToast {
                withCoanaContext("Example") {
                    makeToast(properties)
                }
            }
        }
    }

    private fun bindInputs() {
        progress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                properties += CoanaProperty(SubmitProgress, progress.toDouble() / seekBar.max)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        note.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                properties = s.toString().takeUnless { it.isBlank() }?.let {
                    properties + CoanaProperty(SubmitNote, it)
                } ?: properties.minusKey(SubmitNote)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}
