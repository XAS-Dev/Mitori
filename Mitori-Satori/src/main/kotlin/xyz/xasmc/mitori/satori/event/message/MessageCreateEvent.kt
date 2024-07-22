package xyz.xasmc.mitori.satori.event.message

import xyz.xasmc.mitori.satori.datatype.channel.Channel
import xyz.xasmc.mitori.satori.datatype.message.Message
import xyz.xasmc.mitori.satori.datatype.user.User
import xyz.xasmc.mitori.satori.event.BaseEvent

class MessageCreateEvent(
    override val channel: Channel,
    override val message: Message,
    override val operator: User,
) : BaseEvent("message-created")