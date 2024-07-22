package xyz.xasmc.mitori.satori.api

import xyz.xasmc.mitori.satori.datatype.PagingList
import xyz.xasmc.mitori.satori.datatype.guildMember.GuildMember
import xyz.xasmc.mitori.satori.exception.ApiMethodNotAllowedException

interface GuildMemberApiHandler {
    fun guildMemberGet(guildId: String, userId: String): GuildMember {
        throw ApiMethodNotAllowedException()
    }

    fun guildMemberList(guildId: String, next: String?): PagingList<GuildMember> {
        throw ApiMethodNotAllowedException()
    }

    fun guildMemberKick(guildId: String, userId: String, permanent: Boolean?) {
        throw ApiMethodNotAllowedException()
    }

    fun guildMemberMute(guildId: String, userId: String, duration: Number) {
        throw ApiMethodNotAllowedException()
    }

    fun guildMemberApprove(messageId: String, approve: Boolean, comment: String?) {
        throw ApiMethodNotAllowedException()
    }
}