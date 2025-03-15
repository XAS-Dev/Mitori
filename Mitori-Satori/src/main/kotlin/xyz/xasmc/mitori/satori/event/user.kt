package xyz.xasmc.mitori.satori.event

import xyz.xasmc.mitori.satori.datatype.user.User

fun friendRequestEvent(
    user: User, block: BaseEvent.() -> Unit = {}
) = BaseEvent("friend-request", user = user).apply(block)