package xyz.xasmc.mitori.mitorivelocity.impl

import xyz.xasmc.mitori.mitorivelocity.util.MessageUtil
import xyz.xasmc.mitori.satori.api.MessageApiHandler
import xyz.xasmc.mitori.satori.datatype.message.Message

class MessageApiImpl : MessageApiHandler {
    override fun messageCreate(channelId: String, content: String): List<Message> {
        MessageUtil.sendSatoriMessage(channelId, content)
        val message = Message(
            "114514", // TODO: message 存储系统
            content
        )
        return listOf(message)
    }
}