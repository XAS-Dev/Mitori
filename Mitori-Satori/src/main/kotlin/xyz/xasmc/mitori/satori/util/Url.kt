package xyz.xasmc.mitori.satori.util

class Url(private val url: String) {
    fun resolve(str: String?): Url {
        if (str == null) return this
        val sb = StringBuilder(this.url)
        if (!sb.endsWith("/")) sb.append("/")
        sb.append(str)
        return Url(sb.toString())
    }

    operator fun div(str: String?) = this.resolve(str)
    override fun toString() = url
}