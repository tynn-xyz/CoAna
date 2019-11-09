package xyz.tynn.coana

import xyz.tynn.coana.metadata.MetaData.Key

interface CoanaProperty<Value> : Key<CoanaValue<Value>>
