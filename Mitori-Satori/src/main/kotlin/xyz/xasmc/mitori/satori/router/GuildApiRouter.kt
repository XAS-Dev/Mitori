package xyz.xasmc.mitori.satori.router

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.xasmc.mitori.satori.api.GuildApiHandler
import xyz.xasmc.mitori.satori.datatype.api.guild.GuildApprove
import xyz.xasmc.mitori.satori.datatype.api.guild.GuildGet
import xyz.xasmc.mitori.satori.datatype.api.guild.GuildList

fun Route.guildApiRouter(handler: GuildApiHandler) {
    post("guild.get") {
        val data = call.receive<GuildGet>()
        val result = handler.guildGet(data.guild_id)
        call.respond(HttpStatusCode.OK, result)
    }
    post("guild.list") {
        val data = call.receive<GuildList>()
        val result = handler.guildList(data.next)
        call.respond(HttpStatusCode.OK, result)
    }
    post("guild.approve") {
        val data = call.receive<GuildApprove>()
        handler.guildApprove(data.message_id, data.approve, data.comment)
        call.respond(HttpStatusCode.OK)
    }
}