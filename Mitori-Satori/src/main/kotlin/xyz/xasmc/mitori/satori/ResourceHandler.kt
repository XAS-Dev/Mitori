package xyz.xasmc.mitori.satori

interface ResourceHandler {
    fun getResource(id: String): Pair<ByteArray, String>
}