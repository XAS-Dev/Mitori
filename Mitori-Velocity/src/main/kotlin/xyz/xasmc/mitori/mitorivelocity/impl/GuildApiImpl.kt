package xyz.xasmc.mitori.mitorivelocity.impl

import xyz.xasmc.mitori.mitorivelocity.MitoriVelocity
import xyz.xasmc.mitori.satori.api.GuildApiHandler
import xyz.xasmc.mitori.satori.datatype.PagingList
import xyz.xasmc.mitori.satori.datatype.guild.Guild
import xyz.xasmc.mitori.satori.exception.ApiNotFoundException

class GuildApiImpl : GuildApiHandler {
    private val plugin = MitoriVelocity.instant

    override fun guildGet(guildId: String): Guild {
        if (guildId != "velocity") throw ApiNotFoundException()
        return plugin.proxyGuild
    }

    override fun guildList(next: String?): PagingList<Guild> {
        return PagingList(listOf(plugin.proxyGuild))
    }
}