package xyz.xasmc.mitori.satori.api

import xyz.xasmc.mitori.satori.datatype.PagingList
import xyz.xasmc.mitori.satori.datatype.channel.Channel
import xyz.xasmc.mitori.satori.exception.ApiMethodNotAllowedException

interface ChannelApiHandler {
    fun channelGet(channelId: String): Channel {
        throw ApiMethodNotAllowedException()
    }

    fun channelList(guildId: String, next: String?): PagingList<Channel> {
        throw ApiMethodNotAllowedException()
    }

    fun channelCreate(guildId: String, data: Channel): Channel {
        throw ApiMethodNotAllowedException()
    }

    fun channelUpdate(channelId: String, data: Channel) {
        throw ApiMethodNotAllowedException()
    }

    fun channelDelete(channelId: String) {
        throw ApiMethodNotAllowedException()
    }

    fun channelMute(channelId: String, duration: Number) {
        throw ApiMethodNotAllowedException()
    }

    fun userChannelCreate(userId: String, guildId: String?): Channel {
        throw ApiMethodNotAllowedException()
    }
}