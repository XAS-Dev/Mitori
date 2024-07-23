package xyz.xasmc.mitori.satori.util.xmlParser

open class XmlElement(
    val name: String,
    val attributes: Map<String, String>,
    val children: List<XmlElement>,
) {
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("<").append(name)
        attributes.forEach {
            sb.append(" ").append(it.key)
            if (it.value.isNotEmpty()) sb.append("=\"").append(it.value).append("\"")
        }
        if (children.isNotEmpty()) {
            sb.append(">")
            children.forEach { sb.append(it.toString()) }
            sb.append("</").append(name).append(">")
        } else {
            sb.append("/>")
        }
        return sb.toString()
    }
}