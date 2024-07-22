package xyz.xasmc.mitori.satori.router

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.xasmc.mitori.satori.api.MessageApiHandler
import xyz.xasmc.mitori.satori.datatype.api.message.*

fun Route.messageApiRouter(handler: MessageApiHandler) {
    post("message.create") {
        val data = call.receive<MessageCreate>()
        val result = handler.messageCreate(data.channel_id, data.content)
        call.respond(HttpStatusCode.OK, result)
    }
    post("message.get") {
        val data = call.receive<MessageGet>()
        val result = handler.messageGet(data.channel_id, data.message_id)
        call.respond(HttpStatusCode.OK, result)
    }
    post("message.delete") {
        val data = call.receive<MessageDelete>()
        handler.messageDelete(data.channel_id, data.message_id)
        call.respond(HttpStatusCode.OK)
    }
    post("message.update") {
        val data = call.receive<MessageUpdate>()
        handler.messageUpdate(data.channel_id, data.message_id, data.content)
        call.respond(HttpStatusCode.OK)
    }
    post("message.list") {
        val data = call.receive<MessageList>()
        val result = handler.messageList(data.channel_id, data.next, data.direction, data.limit, data.order)
        call.respond(HttpStatusCode.OK, result)
    }
}