package xyz.xasmc.mitori.mitorivelocity.impl

import xyz.xasmc.mitori.mitorivelocity.MitoriVelocity
import xyz.xasmc.mitori.satori.api.ChannelApiHandler
import xyz.xasmc.mitori.satori.datatype.PagingList
import xyz.xasmc.mitori.satori.datatype.channel.Channel
import xyz.xasmc.mitori.satori.datatype.channel.ChannelType
import xyz.xasmc.mitori.satori.exception.ApiNotFoundException

class ChannelApiImpl : ChannelApiHandler {
    private val plugin = MitoriVelocity.instant
    private val server = plugin.proxyServer

    override fun channelGet(channelId: String): Channel {
        val channelServerOptional = server.getServer(channelId)
        if (!channelServerOptional.isPresent) throw ApiNotFoundException()
        val channelServer = channelServerOptional.get()
        val serverName = channelServer.serverInfo.name
        return Channel(serverName, ChannelType.TEXT, serverName, "velocity")
    }

    override fun channelList(guildId: String, next: String?): PagingList<Channel> {
        if (guildId != "velocity") throw ApiNotFoundException()
        val result = mutableListOf<Channel>()
        server.allServers.forEach {
            result.add(Channel(it.serverInfo.name, ChannelType.TEXT, it.serverInfo.name, "velocity"))
        }
        return PagingList(result)
    }
}