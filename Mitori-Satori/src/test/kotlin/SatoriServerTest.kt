import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.jupiter.api.Test
import xyz.xasmc.mitori.satori.HandlerConfig
import xyz.xasmc.mitori.satori.SatoriServer
import xyz.xasmc.mitori.satori.ResourceHandler
import xyz.xasmc.mitori.satori.SatoriConfig
import xyz.xasmc.mitori.satori.api.*
import xyz.xasmc.mitori.satori.datatype.api.channel.ChannelGet
import xyz.xasmc.mitori.satori.datatype.channel.Channel
import xyz.xasmc.mitori.satori.datatype.channel.ChannelType
import kotlin.test.assertEquals


class SatoriServerTest() {
    private val satoriConfig = SatoriConfig(port = 5550, token = "114514")
    private val handlerConfig = HandlerConfig(
        channel = object : ChannelApiHandler {
            override fun channelGet(channelId: String): Channel {
                assertEquals(channelId, "114514")
                return Channel("114514", ChannelType.TEXT, "114514")
            }
        },
        guild = object : GuildApiHandler {},
        guildMember = object : GuildMemberApiHandler {},
        guildRole = object : GuildRoleApiHandler {},
        login = object : LoginApiHandler {},
        message = object : MessageApiHandler {},
        reaction = object : ReactionApiHandler {},
        user = object : UserApiHandler {},
    )
    private val resourceHandler = object : ResourceHandler {
        override fun getResource(id: String): ResourceHandler.Resource {
            return ResourceHandler.Resource(ByteArray(0), "")
        }
    }

    private val satoriServer = SatoriServer(satoriConfig, handlerConfig, resourceHandler)
    private val client = HttpClient()

    @Test
    fun testRoot() {
        satoriServer.start()
        val result = runBlocking { client.get(satoriConfig.link) }
        assertEquals(result.status, HttpStatusCode.OK)
        satoriServer.stopBlocking()
    }

    @Test
    fun testChannelGet() {
        satoriServer.start()

        val result = runBlocking {
            client.post(satoriConfig.link + satoriConfig.path + "/channel.get") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer 114514")
                setBody(Json.encodeToString(serializer(), ChannelGet("114514")))
            }
        }
        val body = runBlocking { result.bodyAsText() }
        assertEquals(result.status, HttpStatusCode.OK)
        assertEquals(Json.decodeFromString<Channel>(body), Channel("114514", ChannelType.TEXT, "114514"))
        satoriServer.stopBlocking()
    }
}