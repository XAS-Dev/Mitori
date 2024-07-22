package xyz.xasmc.mitori.satori.event.user

import xyz.xasmc.mitori.satori.datatype.user.User
import xyz.xasmc.mitori.satori.event.BaseEvent

class FriendRequestEvent(
    override val user: User,
) : BaseEvent("friend-request")