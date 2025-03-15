package xyz.xasmc.mitori.satori

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Job
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
import xyz.xasmc.mitori.satori.util.URI
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

class SatoriServer(
    private val config: SatoriConfig,
    private val handlerConfig: HandlerConfig,
    private val resourceHandler: ResourceHandler
) {
    companion object {
        private const val AUTH_TIMEOUT_MS = 30_000L
        private const val CLOSE_CODE_AUTH_TIMEOUT = 1000
        private const val CLOSE_CODE_UNAUTHORIZED = 3000
        private const val CLOSE_CODE_INVALID_PAYLOAD = 4000
    }


    private val baseUri = URI() / config.path / "v1"
    private val baseLink = config.link.removeSuffix("/") + baseUri

    private val httpServer = createServer()
    private val eventSn = AtomicLong(0)
    private val loginSn = AtomicLong(0)
    private val authedConnections = ConcurrentHashMap.newKeySet<WebSocketServerSession>()

    private fun createServer() = embeddedServer(Netty, host = config.host, port = config.port) {
        install(WebSockets)
        install(ContentNegotiation) { json(Json { encodeDefaults = false }) }
        install(StatusPages) {
            exception<SatoriApiException> { call, cause ->
                val text = cause.description
                val status = cause.httpCode
                call.respondText(text, ContentType.Text.Plain, HttpStatusCode.fromValue(status))
            }
            exception<Throwable> { call, cause ->
                call.respondText("Internal server error.", status = HttpStatusCode.InternalServerError)
                cause.printStackTrace(System.err)
            }
        }
        routing {
            get("/") {
                call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            }
            eventRouter()
            apiRouter()
            resourceRouter()
        }
    }

    // === router ===

    private fun Route.eventRouter() {
        val path = baseUri / "events"
        webSocket(path.toString()) {
            val authTimerJob = launch {
                delay(AUTH_TIMEOUT_MS)
                closeWs(this@webSocket, 1000, "Authentication timeout")
            }
            val context = WsContext(this, authTimerJob)
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                processWsFrame(context, frame)
            }
            authTimerJob.cancel()
            authedConnections.remove(this)
        }
    }

    private fun Route.apiRouter() {
        route(baseUri.toString()) {
            intercept(Plugins) {
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
    }

    private fun Route.resourceRouter() {
        val path = baseUri / "resource" / "{id...}"
        get(path.toString()) {
            // TODO
            println(call.parameters)
            println(call.parameters.getAll("id"))
            val resourceId = call.parameters.getAll("id")?.joinToString("/") ?: return@get
            println(resourceId)
            val resource = resourceHandler.getResource(resourceId) ?: return@get
            val data = resource.data
            val type = when {
                resource.type != null -> ContentType.parse(resource.type)
                resource.extension != null -> ContentType.fromFileExtension(resource.extension).getOrNull(0)
                else -> null
            } ?: ContentType.Any
            call.respondBytes(data, type)
        }
    }

    // === ws ===

    private data class WsContext(
        val session: WebSocketServerSession, val authTimerJob: Job
    )

    private suspend fun processWsFrame(context: WsContext, frame: Frame.Text) {
        val data = runCatching { Json.decodeFromString<Payload>(frame.readText()) }.getOrNull() ?: return
        processWsData(context, data.op, data.body)
    }

    private suspend fun processWsData(context: WsContext, op: Opcode, body: JsonObject? = null) {
        val session = context.session
        when (op) {
            Opcode.PING -> sendWsData(session, Opcode.PONG)
            Opcode.IDENTIFY -> processWsAuth(context, body)
            else -> return
        }
    }

    private suspend fun processWsAuth(context: WsContext, body: JsonObject?) {
        val session = context.session
        if (authedConnections.contains(session)) return
        val identifyBody = runCatching { Json.decodeFromJsonElement<Identify>(body ?: return) }.getOrNull() ?: return
        val token = identifyBody.token
        // 验证token
        if (token != config.token) {
            context.authTimerJob.cancel()
            closeWs(session, 3000, "Unauthorized")
            return
        }
        // 发送数据
        val loginData = getLogin()
        val sendBody = Ready(logins = listOf(loginData))
        sendWsData(session, Opcode.READY, Json.encodeToJsonElement(serializer(), sendBody).jsonObject)
        authedConnections.add(session)
    }

    private suspend fun sendWsData(session: WebSocketServerSession, op: Opcode, body: JsonObject? = null) {
        val payload = Payload(op, body)
        session.send(Json.encodeToString(serializer(), payload))
    }

    private suspend fun closeWs(session: WebSocketServerSession, code: Short, reason: String) {
        session.close(CloseReason(code, reason))
    }

    // ===== API =====

    suspend fun emitEvent(event: BaseEvent) {
        val sendEvent = Event(
            sn = eventSn.getAndIncrement(),
            type = event.type,
            timestamp = Instant.now().toEpochMilli(),
            login = event.login,
            argv = event.argv,
            button = event.button,
            channel = event.channel,
            guild = event.guild,
            member = event.member,
            message = event.message,
            operator = event.operator,
            role = event.role,
            user = event.user,
        )
        authedConnections.forEach {
            sendWsData(it, Opcode.EVENT, Json.encodeToJsonElement(serializer(), sendEvent).jsonObject)
        }
    }

    fun emitEventBlocking(event: BaseEvent) = runBlocking { emitEvent(event) }

    fun start() {
        httpServer.start(wait = false)
    }

    suspend fun stop() {
        authedConnections.forEach { closeWs(it, CloseReason.Codes.GOING_AWAY.code, "Server shutdown") }
        httpServer.stop()
    }

    fun stopBlocking() = runBlocking { stop() }

    fun makeResourceLink(id: String) = "${baseLink}/${id}"

    fun getLogin() = Login(
        sn = loginSn.getAndIncrement(),
        user = User(
            id = config.selfId,
            name = config.selfName,
            is_bot = true,
        ),
        platform = "Mitori",
        status = Status.ONLINE,
        adapter = "Mitori",
    )
}
