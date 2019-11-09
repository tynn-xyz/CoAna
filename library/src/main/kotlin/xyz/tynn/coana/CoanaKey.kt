package xyz.tynn.coana

import xyz.tynn.coana.metadata.MetaData.Key

internal sealed class CoanaKey : Key<CoanaValue<String>> {
    object Scope : CoanaKey()
    object Context : CoanaKey()
}
