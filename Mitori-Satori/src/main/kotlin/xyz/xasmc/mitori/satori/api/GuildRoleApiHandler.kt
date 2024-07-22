package xyz.xasmc.mitori.satori.api

import xyz.xasmc.mitori.satori.datatype.PagingList
import xyz.xasmc.mitori.satori.datatype.guildRole.GuildRole
import xyz.xasmc.mitori.satori.exception.ApiMethodNotAllowedException

interface GuildRoleApiHandler {
    fun guildMemberRoleSet(guildId: String, userId: String, roleId: String) {
        throw ApiMethodNotAllowedException()
    }

    fun guildMemberRoleUnset(guildId: String, userId: String, roleId: String) {
        throw ApiMethodNotAllowedException()
    }

    fun guildRoleList(guildId: String, next: String?): PagingList<GuildRole> {
        throw ApiMethodNotAllowedException()
    }

    fun guildRoleCreate(guildId: String, role: GuildRole): GuildRole {
        throw ApiMethodNotAllowedException()
    }

    fun guildRoleUpdate(guildId: String, roleId: String, role: GuildRole) {
        throw ApiMethodNotAllowedException()
    }

    fun guildRoleDelete(guildId: String, roleId: String) {
        throw ApiMethodNotAllowedException()
    }
}