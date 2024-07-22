package xyz.xasmc.mitori.satori.event.login

import xyz.xasmc.mitori.satori.datatype.login.Login
import xyz.xasmc.mitori.satori.event.BaseEvent

class LoginUpdatedEvent(
    override val login: Login,
) : BaseEvent("login-updated")