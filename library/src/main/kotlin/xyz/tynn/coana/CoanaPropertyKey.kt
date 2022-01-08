//  Copyright 2019 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.coana

import xyz.tynn.coana.metadata.MetaData

/**
 * Abstract definition of a [MetaData.Key] for [Coana] properties.
 */
public interface CoanaPropertyKey<Value> : MetaData.Key<CoanaValue<Value>>
