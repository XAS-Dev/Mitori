package xyz.xasmc.mitori.satori.router

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.xasmc.mitori.satori.api.GuildMemberApiHandler
import xyz.xasmc.mitori.satori.datatype.api.guildMember.*

fun Route.guildMemberApiRouter(handler: GuildMemberApiHandler) {
    post("guild.member.get") {
        val data = call.receive<GuildMemberGet>()
        val result = handler.guildMemberGet(data.guild_id, data.user_id)
        call.respond(HttpStatusCode.OK, result)
    }
    post("guild.member.list") {
        val data = call.receive<GuildMemberList>()
        val result = handler.guildMemberList(data.guild_id, data.next)
        call.respond(HttpStatusCode.OK, result)
    }
    post("guild.member.kick") {
        val data = call.receive<GuildMemberKick>()
        handler.guildMemberKick(data.guild_id, data.user_id, data.permanent)
        call.respond(HttpStatusCode.OK)
    }
    post("guild.member.mute") {
        val data = call.receive<GuildMemberMute>()
        handler.guildMemberMute(data.guild_id, data.user_id, data.duration)
        call.respond(HttpStatusCode.OK)
    }
    post("guild.member.approve") {
        val data = call.receive<GuildMemberApprove>()
        handler.guildMemberApprove(data.message_id, data.approve, data.comment)
        call.respond(HttpStatusCode.OK)
    }
}
