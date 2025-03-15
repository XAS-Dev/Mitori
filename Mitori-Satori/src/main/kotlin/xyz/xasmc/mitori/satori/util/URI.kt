package xyz.xasmc.mitori.satori.util

class URI(uri: String = "") {
    private val uri = normalizeUri(uri)
    private fun getParts(str: String? = null) = (str ?: uri).split("/").filter { it.isNotEmpty() }
    private fun normalizeUri(str: String): String {
        val sb = StringBuilder("/")
        sb.append(getParts(str).joinToString("/"))
        if (str.endsWith("/")) sb.append("/")
        return sb.toString()
    }

    operator fun div(str: String?): URI {
        if (str == null) return this
        val sb = StringBuilder(this.uri)
        if (!sb.endsWith("/")) sb.append("/")
        sb.append(str)
        return URI(sb.toString())
    }

    override fun toString() = uri

    override fun equals(other: Any?): Boolean = when (other) {
        is URI -> this.uri.removeSuffix("/") == other.uri.removeSuffix("/")
        is String -> this.uri.removeSuffix("/") == normalizeUri(other).removeSuffix("/")
        else -> false
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    fun isBaseOn(other: URI): Boolean {
        val thisParts = this.getParts()
        val otherParts = other.getParts()
        if (thisParts.size < otherParts.size) return false
        for ((index, otherPart) in otherParts.withIndex()) {
            val thisPart = thisParts[index]
            if (thisPart != otherPart) return false
        }
        return !(thisParts.size == otherParts.size && this.uri.endsWith("/") && !other.uri.endsWith("/"))
    }

    fun isBaseOn(other: String) = isBaseOn(URI(other))

    val name get() = getParts().lastOrNull()
    val parent get() = getParts().takeIf { it.isNotEmpty() }?.let { URI(it.dropLast(1).joinToString("/")) } ?: this

}