package xyz.xasmc.mitori.satori.router

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.xasmc.mitori.satori.api.GuildRoleApiHandler
import xyz.xasmc.mitori.satori.datatype.api.guildRole.*

fun Route.guildRoleApiRouter(handler: GuildRoleApiHandler) {
    post("guild.member.role.set") {
        val data = call.receive<GuildMemberRoleSet>()
        handler.guildMemberRoleSet(data.guild_id, data.user_id, data.role_id)
        call.respond(HttpStatusCode.OK)
    }
    post("guild.member.role.unset") {
        val data = call.receive<GuildMemberRoleUnset>()
        handler.guildMemberRoleUnset(data.guild_id, data.user_id, data.role_id)
        call.respond(HttpStatusCode.OK)
    }
    post("guild.role.list") {
        val data = call.receive<GuildRoleList>()
        val result = handler.guildRoleList(data.guild_id, data.next)
        call.respond(HttpStatusCode.OK, result)
    }
    post("guild.role.create") {
        val data = call.receive<GuildRoleCreate>()
        val result = handler.guildRoleCreate(data.guild_id, data.role)
        call.respond(HttpStatusCode.OK, result)
    }
    post("guild.role.update") {
        val data = call.receive<GuildRoleUpdate>()
        handler.guildRoleUpdate(data.guild_id, data.role_id, data.role)
        call.respond(HttpStatusCode.OK)
    }
    post("guild.role.delete") {
        val data = call.receive<GuildRoleDelete>()
        handler.guildRoleDelete(data.guild_id, data.role_id)
        call.respond(HttpStatusCode.OK)
    }
}
