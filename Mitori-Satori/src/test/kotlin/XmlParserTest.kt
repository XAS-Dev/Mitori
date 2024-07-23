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
        1919810&quot;&#x30;
        awawawaw
        <!-- awa -->
        <p>
            awa
            <a href="/login">login</a>
            qwqwqwq
        </p>
        """.trimIndent()
        )
        println(result)
        assertEquals(
            "<a href=\"awa\" aaa>aLink</a>1145144<br/>1919810&quot;0<br/>awawawaw<p>awa<a href=\"/login\">login</a>qwqwqwq</p>",
            result.toString(),
        )
    }
}