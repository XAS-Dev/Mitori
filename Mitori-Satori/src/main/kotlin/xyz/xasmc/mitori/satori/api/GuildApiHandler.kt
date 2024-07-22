package xyz.xasmc.mitori.satori.api

import xyz.xasmc.mitori.satori.datatype.PagingList
import xyz.xasmc.mitori.satori.datatype.guild.Guild
import xyz.xasmc.mitori.satori.exception.ApiMethodNotAllowedException

interface GuildApiHandler {
    fun guildGet(guildId: String): Guild {
        throw ApiMethodNotAllowedException()
    }

    fun guildList(next: String?): PagingList<Guild> {
        throw ApiMethodNotAllowedException()
    }

    fun guildApprove(messageId: String, approve: Boolean, comment: String?) {
        throw ApiMethodNotAllowedException()
    }
}
