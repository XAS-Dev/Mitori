package xyz.xasmc.mitori.satori.event

import xyz.xasmc.mitori.satori.datatype.channel.Channel
import xyz.xasmc.mitori.satori.datatype.message.Message
import xyz.xasmc.mitori.satori.datatype.user.User

fun messageCreateEvent(
    channel: Channel, message: Message, operator: User, block: BaseEvent.() -> Unit = {}
) = BaseEvent("message-created", channel = channel, message = message, operator = operator).apply(block)

fun messageDeletedEvent(
    channel: Channel, message: Message, operator: User, block: BaseEvent.() -> Unit = {}
) = BaseEvent("message-delete", channel = channel, message = message, operator = operator).apply(block)

fun messageUpdatedEvent(
    channel: Channel, message: Message, operator: User, block: BaseEvent.() -> Unit = {}
) = BaseEvent("message-updated", channel = channel, message = message, operator = operator).apply(block)