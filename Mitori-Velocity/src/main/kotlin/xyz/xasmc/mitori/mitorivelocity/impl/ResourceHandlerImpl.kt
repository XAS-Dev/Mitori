package xyz.xasmc.mitori.mitorivelocity.impl

import xyz.xasmc.mitori.satori.ResourceHandler

class ResourceHandlerImpl : ResourceHandler {
    override fun getResource(id: String): Pair<ByteArray, String> {
        return Pair(ByteArray(0), "")
    }
}
