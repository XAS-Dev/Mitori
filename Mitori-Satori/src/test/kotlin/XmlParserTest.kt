import org.junit.jupiter.api.Test
import xyz.xasmc.mitori.satori.util.xmlParser.XmlDocument
import kotlin.test.assertEquals

class XmlParserTest {
    @Test
    fun testXmlParser() {
        val result = XmlDocument.fromString(
            """
        <a href="awa" aaa>aLink</a>
        1145144<br/>
        1919810
        awawawaw
        <p>
            awa
            <a href="/login">login</a>
            qwqwqwq
        </p>
        """.trimIndent()
        )
        assertEquals(
            result.toString(),
            "<a href=\"awa\" aaa>aLink</a>1145144<br/>1919810<br/>awawawaw<br/><p>awa<br/><a href=\"/login\">login</a>qwqwqwq<br/></p>"
        )
    }
}