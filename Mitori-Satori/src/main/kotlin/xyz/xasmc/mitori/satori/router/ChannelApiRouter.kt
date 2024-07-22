package xyz.xasmc.mitori.satori.router

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.xasmc.mitori.satori.api.ChannelApiHandler
import xyz.xasmc.mitori.satori.datatype.api.channel.*

fun Route.channelApiRouter(handler: ChannelApiHandler) {
    post("channel.get") {
        val data = call.receive<ChannelGet>()
        val result = handler.channelGet(data.channel_id)
        call.respond(HttpStatusCode.OK, result)
    }
    post("channel.list") {
        val data = call.receive<ChannelList>()
        val result = handler.channelList(data.guild_id, data.next)
        call.respond(HttpStatusCode.OK, result)
    }
    post("channel.create") {
        val data = call.receive<ChannelCreate>()
        val result = handler.channelCreate(data.guild_id, data.data)
        call.respond(HttpStatusCode.OK, result)
    }
    post("channel.update") {
        val data = call.receive<ChannelUpdate>()
        handler.channelUpdate(data.channel_id, data.data)
        call.respond(HttpStatusCode.OK)
    }
    post("channel.delete") {
        val data = call.receive<ChannelDelete>()
        handler.channelDelete(data.channel_id)
        call.respond(HttpStatusCode.OK)
    }
    post("channel.mute") {
        val data = call.receive<ChannelMute>()
        handler.channelMute(data.channel_id, data.duration)
        call.respond(HttpStatusCode.OK)
    }
    post("user.channel.create") {
        val data = call.receive<UserChannelCreate>()
        val result = handler.userChannelCreate(data.user_id, data.guild_id)
        call.respond(HttpStatusCode.OK, result)
    }
}