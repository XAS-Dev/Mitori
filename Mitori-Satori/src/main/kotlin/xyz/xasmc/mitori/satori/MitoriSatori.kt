package xyz.xasmc.mitori.satori

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer
import xyz.xasmc.mitori.satori.datatype.Event
import xyz.xasmc.mitori.satori.datatype.Opcode
import xyz.xasmc.mitori.satori.datatype.login.Login
import xyz.xasmc.mitori.satori.datatype.login.Status
import xyz.xasmc.mitori.satori.datatype.signal.Identify
import xyz.xasmc.mitori.satori.datatype.signal.Payload
import xyz.xasmc.mitori.satori.datatype.signal.Ready
import xyz.xasmc.mitori.satori.datatype.user.User
import xyz.xasmc.mitori.satori.event.BaseEvent
import xyz.xasmc.mitori.satori.exception.SatoriApiException
import xyz.xasmc.mitori.satori.router.*
import xyz.xasmc.mitori.satori.util.Url
import java.time.Instant

class MitoriSatori(
    private val config: SatoriConfig,
    private val handlerConfig: HandlerConfig,
    private val resourceHandler: ResourceHandler
) {
    val baseLink = "http://${config.host}:${config.port}"
    val baseUrl = Url("/") / config.path / "v1"
    private var currentId: Long = 0
    private val authedConnections = HashSet<WebSocketServerSession>()
    val server: ApplicationEngine = embeddedServer(Netty, host = config.host, port = config.port) {
        install(WebSockets)
        install(ContentNegotiation) { json(Json { encodeDefaults = false }) }
        install(StatusPages) {
            exception<SatoriApiException> { call, cause ->
                val text = cause.getDescription()
                val status = cause.getHttpCode()
                call.respondText(text, ContentType.Text.Plain, HttpStatusCode.fromValue(status))
            }
        }
        routing {
            get("/") {
                call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            }
            route(baseUrl.toString()) {
                webSocket("events") {
                    launch {
                        delay(30 * 1000) // 30秒超时
                        if (!authedConnections.contains(this@webSocket)) closeWs(
                            this@webSocket, 1000, "Authentication timeout"
                        )
                    }
                    for (frame in incoming) {
                        frame as? Frame.Text ?: continue
                        val data = Json.decodeFromString<Payload>(frame.readText())
                        val op = data.op
                        val body = data.body
                        onWsPayload(this, op, body)
                    }
                    authedConnections.remove(this)
                }
                intercept(Plugins) {
                    if (call.request.uri.startsWith((baseUrl / "events").toString())) return@intercept
                    val authHeader = call.request.headers["Authorization"]
                    if (authHeader == null && config.token == null) return@intercept
                    if (authHeader != null && config.token != null) {
                        val regex = Regex("^Bearer (.*)$")
                        val matchResult = regex.matchEntire(authHeader)
                        if (matchResult?.groupValues?.get(1) == config.token) return@intercept
                    }
                    call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
                    finish()
                }
                channelApiRouter(handlerConfig.channel)
                guildApiRouter(handlerConfig.guild)
                guildMemberApiRouter(handlerConfig.guildMember)
                guildRoleApiRouter(handlerConfig.guildRole)
                loginApiRouter(handlerConfig.login)
                messageApiRouter(handlerConfig.message)
                reactionApiRouter(handlerConfig.reaction)
                userApiRouter(handlerConfig.user)
            }
            route("/resource") {
                get("{id}") {
                    val resourceId = call.request.queryParameters["id"] ?: return@get
                    val (data, filename) = resourceHandler.getResource(resourceId)
                    val types = ContentType.fromFileExtension(filename.split('.').last())
                    call.respondBytes(data, if (types.isNotEmpty()) types[0] else ContentType.Image.Any)
                }
            }
        }
    }

    private suspend fun onWsPayload(session: WebSocketServerSession, op: Opcode, body: JsonObject? = null) {
        when (op) {
            Opcode.PING -> sendWsPayload(session, Opcode.PONG)
            Opcode.IDENTIFY -> {
                if (authedConnections.contains(session)) return
                val identifyBody = Json.decodeFromJsonElement<Identify>(body ?: return)
                val token = identifyBody.token
                if (token != config.token) {
                    closeWs(session, 3000, "Unauthorized")
                    return
                }
                val sendBody = Ready(
                    logins = listOf(
                        Login(
                            user = User(
                                id = config.selfId,
                                name = config.selfName,
                                is_bot = true,
                            ),
                            self_id = config.selfId,
                            platform = "Mitori",
                            status = Status.ONLINE,
                        )
                    )
                )
                sendWsPayload(session, Opcode.READY, Json.encodeToJsonElement(serializer(), sendBody).jsonObject)
                authedConnections.add(session)
            }

            else -> return
        }
    }

    private suspend fun sendWsPayload(session: WebSocketServerSession, op: Opcode, body: JsonObject? = null) {
        val payload = Payload(op, body)
        session.send(Json.encodeToString(serializer(), payload))
    }

    private suspend fun closeWs(session: WebSocketServerSession, code: Short, reason: String) {
        session.close(CloseReason(code, reason))
    }

    fun emit(event: BaseEvent) {
        val sendEvent = Event(
            id = currentId,
            type = event.type,
            platform = config.platform,
            self_id = config.selfId,
            timestamp = Instant.now().toEpochMilli(),
            argv = event.argv,
            button = event.button,
            channel = event.channel,
            guild = event.guild,
            login = event.login,
            member = event.member,
            message = event.message,
            operator = event.operator,
            role = event.role,
            user = event.user,
        )
        currentId++
        runBlocking {
            authedConnections.forEach {
                sendWsPayload(it, Opcode.EVENT, Json.encodeToJsonElement(serializer(), sendEvent).jsonObject)
            }
        }
    }

    fun start() {
        server.start(wait = false)
    }

    fun stop() {
        server.stop()
    }
}
