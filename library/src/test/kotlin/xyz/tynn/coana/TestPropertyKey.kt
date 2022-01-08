//  Copyright 2019 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.coana

internal sealed class TestPropertyKey<V> : CoanaPropertyKey<V> {
    object Double : TestPropertyKey<kotlin.Double>()
    object Long : TestPropertyKey<kotlin.Long>()
    object String : TestPropertyKey<kotlin.String>()
}
