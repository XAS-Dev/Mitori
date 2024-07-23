package xyz.xasmc.mitori.satori.util.xmlParser

object EscapeUtil {
    fun escape(string: String): String {
        val processedString = string
            .replace("&quot;", "\"")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&amp", "&")
        val regex = Regex("""&#(x?[0-9]+);""")
        return regex.replace(processedString) {
            val numStr = it.groupValues[1]
            val num = if (numStr.startsWith("x")) numStr.removePrefix("x").toInt(16) else numStr.toInt()
            num.toChar().toString()
        }
    }

    fun unescape(string: String) = string
        .replace("&", "&amp")
        .replace("\"", "&quot;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
}