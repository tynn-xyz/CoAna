//  Copyright 2019 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.coana.metadata.test

import xyz.tynn.coana.metadata.MetaData

object TestKey : MetaData.Key<String> {
    @Suppress("UNCHECKED_CAST")
    fun <T> cast() = this as MetaData.Key<T>
}
