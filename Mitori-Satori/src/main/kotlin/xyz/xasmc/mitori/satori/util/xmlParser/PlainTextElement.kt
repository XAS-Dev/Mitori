package xyz.xasmc.mitori.satori.util.xmlParser

class PlainTextElement(val text: String) : XmlElement("text", emptyMap(), emptyList()) {
    override fun toString(): String {
        return text
    }
}