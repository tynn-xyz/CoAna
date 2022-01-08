//  Copyright 2019 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.coana.example

import xyz.tynn.coana.CoanaPropertyKey

internal sealed class ExamplePropertyKey<V> : CoanaPropertyKey<V> {
    object AppVersion : ExamplePropertyKey<String>()
    object SubmitCount : ExamplePropertyKey<Long>()
    object SubmitNote : ExamplePropertyKey<String>()
    object SubmitProgress : ExamplePropertyKey<Double>()

    override fun toString(): String = javaClass.simpleName
}
