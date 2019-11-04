package xyz.tynn.coana

import xyz.tynn.coana.metadata.MetaData.Key

internal sealed class CoAnaKey : Key<CoAnaValue<String>> {
    object Scope : CoAnaKey()
    object Context : CoAnaKey()
}
