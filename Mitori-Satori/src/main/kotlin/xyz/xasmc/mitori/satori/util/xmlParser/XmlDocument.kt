package xyz.xasmc.mitori.satori.util.xmlParser


// TODO: https://satori.chat/zh-CN/protocol/elements.html#message
// 重写罢(悲
class XmlDocument(
    val elements: List<XmlElement>,
) {
    override fun toString() = elements.joinToString("")

    companion object {
        fun fromString(str: String): XmlDocument {
            return XmlDocument(parseStr(str).first)
        }

        private fun parseStr(content: String, parentName: String? = null): Pair<MutableList<XmlElement>, Int> {
            val result = mutableListOf<XmlElement>()
            val sb = StringBuilder()
            var ptr = 0
            while (ptr < content.length) {
                // 处理注释
                if (content.startsWith("<!--", ptr)) {
                    val endCommentIndex = content.indexOf("-->", ptr + 4)
                    ptr = endCommentIndex + 3
                    continue
                }
                // 处理闭标签
                if (content.startsWith("</", ptr)) {
                    if (sb.isNotEmpty()) {
                        result.addAll(parsePlainText(sb.toString()))
                        sb.clear()
                    }
                    val endIndex = content.indexOf(">", ptr + 2)
                    if (endIndex == -1) throw IllegalArgumentException("Unclosed tag: ${content.substring(ptr)}")
                    val closeTagStr = content.substring(ptr..endIndex)
                    val closeTagName = closeTagStr.removeSurrounding("</", ">").trim()
                    if (closeTagName == parentName) return Pair(result, endIndex + 1) // 正确的闭标签, 返回
                    throw IllegalArgumentException("Unknown CloseTag: $closeTagStr")
                }
                // 处理开标签
                if (content[ptr] == '<') {
                    if (sb.isNotEmpty()) {
                        result.addAll(parsePlainText(sb.toString()))
                        sb.clear()
                    }
                    val endIndex = content.indexOf(">", ptr + 1)
                    if (endIndex == -1) throw IllegalArgumentException("Unclosed tag: ${content.substring(ptr)}")
                    if (content[endIndex - 1] == '/') {
                        // 立即关闭
                        val openTagStr = content.substring(ptr..endIndex)
                        val (name, attr) = parseOpenTag(openTagStr)
                        result.add(XmlElement(name, attr, mutableListOf()))
                        ptr = endIndex + 1
                        continue
                    }
                    val openTagStr = content.substring(ptr..endIndex)
                    val (name, attr) = parseOpenTag(openTagStr)
                    // 处理剩下的内容
                    ptr = endIndex + 1
                    val contentStr = content.substring(ptr)
                    val (child, read) = parseStr(contentStr, name)
                    ptr += read
                    result.add(XmlElement(name, attr, child))
                    continue
                }
                // 啥也不是,原始文本
                if (sb.isNotEmpty() || (content[ptr] != '\n' && content[ptr] != ' ')) sb.append(content[ptr])
                ptr++
            }
            if (sb.isNotEmpty()) result.addAll(parsePlainText(sb.toString()))
            return Pair(result, content.length)
        }

        private fun parsePlainText(str: String): MutableList<XmlElement> {
            val result = mutableListOf<XmlElement>()
            val ls = str.trimEnd().split('\n').toMutableList()
            val first = ls.removeFirst()
            result.add(PlainTextElement(EscapeUtil.escape(first)))
            ls.forEach {
                result.add(XmlElement("br", emptyMap(), emptyList()))
                result.add(PlainTextElement(it))
            }
            return result
        }

        private fun parseOpenTag(str: String): Pair<String, MutableMap<String, String>> {
            val regex = Regex("""^< *([a-zA-Z0-9:]+)(?: ((?: *[a-zA-Z0-9:]+(?: *= *"[^"]*")? *)*))? */?>${'$'}""")
            val result = regex.find(str) ?: throw IllegalArgumentException("Can not parse tag: $str")
            val name = result.groupValues[1]

            val attr = mutableMapOf<String, String>()
            val attrStr = result.groupValues[2]
            val attrRegex = Regex("""([a-zA-Z0-9:]+)(?: *= *"([^"]+)")?""")
            attrRegex.findAll(attrStr).forEach {
                val key = it.groupValues[1]
                val value = it.groupValues[2]
                attr[key] = value
            }
            return Pair(name, attr)
        }
    }
}