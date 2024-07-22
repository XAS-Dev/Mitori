package xyz.xasmc.mitori.satori.router

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.xasmc.mitori.satori.api.ReactionApiHandler
import xyz.xasmc.mitori.satori.datatype.api.reaction.ReactionClear
import xyz.xasmc.mitori.satori.datatype.api.reaction.ReactionCreate
import xyz.xasmc.mitori.satori.datatype.api.reaction.ReactionDelete
import xyz.xasmc.mitori.satori.datatype.api.reaction.ReactionList

fun Route.reactionApiRouter(handler: ReactionApiHandler) {
    post("reaction.create") {
        val data = call.receive<ReactionCreate>()
        handler.reactionCreate(data.channel_id, data.message_id, data.emoji)
        call.respond(HttpStatusCode.OK)
    }
    post("reaction.delete") {
        val data = call.receive<ReactionDelete>()
        handler.reactionDelete(data.channel_id, data.message_id, data.emoji, data.user_id)
        call.respond(HttpStatusCode.OK)
    }
    post("reaction.create") {
        val data = call.receive<ReactionClear>()
        handler.reactionClear(data.channel_id, data.message_id, data.emoji)
        call.respond(HttpStatusCode.OK)
    }
    post("reaction.list") {
        val data = call.receive<ReactionList>()
        val result = handler.reactionList(data.channel_id, data.message_id, data.emoji, data.next)
        call.respond(HttpStatusCode.OK, result)
    }
}