package xyz.xasmc.mitori.satori.api

import xyz.xasmc.mitori.satori.datatype.BidiList
import xyz.xasmc.mitori.satori.datatype.Direction
import xyz.xasmc.mitori.satori.datatype.Order
import xyz.xasmc.mitori.satori.datatype.message.Message
import xyz.xasmc.mitori.satori.exception.ApiMethodNotAllowedException

interface MessageApiHandler {
    fun messageCreate(channelId: String, content: String): List<Message> {
        throw ApiMethodNotAllowedException()
    }

    fun messageGet(channelId: String, messageId: String): Message {
        throw ApiMethodNotAllowedException()
    }

    fun messageDelete(channelId: String, messageId: String) {
        throw ApiMethodNotAllowedException()
    }

    fun messageUpdate(channelId: String, messageId: String, content: String) {
        throw ApiMethodNotAllowedException()
    }

    fun messageList(
        channelId: String,
        next: String?,
        direction: Direction?,
        limit: Number?,
        order: Order?,
    ): BidiList<Message> {
        throw ApiMethodNotAllowedException()
    }
}