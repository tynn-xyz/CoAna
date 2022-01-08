//  Copyright 2019 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.coana.example

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import xyz.tynn.coana.CoanaProperty
import xyz.tynn.coana.example.ExamplePropertyKey.SubmitNote
import xyz.tynn.coana.example.ExamplePropertyKey.SubmitProgress
import xyz.tynn.coana.example.databinding.ActivityExampleBinding
import xyz.tynn.coana.example.databinding.ActivityExampleBinding.inflate
import xyz.tynn.coana.withCoanaContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class ExampleActivity : Activity() {

    private lateinit var toaster: ExampleToaster

    private var properties: CoroutineContext = EmptyCoroutineContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toaster = ExampleToaster()
        with(inflate(layoutInflater)) {
            setContentView(root)
            bindButtons()
            bindInputs()
        }
    }

    private fun ActivityExampleBinding.bindButtons() {
        submit.setOnClickListener {
            with(toaster) {
                prepareToast {
                    makeToast(properties)
                }
            }
        }

        submitContext.setOnClickListener {
            with(toaster) {
                prepareToast {
                    withCoanaContext("Example") {
                        makeToast(properties)
                    }
                }
            }
        }
    }

    private fun ActivityExampleBinding.bindInputs() {
        progress.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
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

    override fun onDestroy() {
        toaster.destroy()
        super.onDestroy()
    }
}
