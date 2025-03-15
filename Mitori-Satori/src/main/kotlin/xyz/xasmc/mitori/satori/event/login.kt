package xyz.xasmc.mitori.satori.event

import xyz.xasmc.mitori.satori.datatype.login.Login

fun loginAddedEvent(
    login: Login, block: BaseEvent.() -> Unit = {}
) = BaseEvent("login-added", login = login).apply(block)

fun loginRemovedEvent(
    login: Login, block: BaseEvent.() -> Unit = {}
) = BaseEvent("login-removed", login = login).apply(block)


fun loginUpdatedEvent(
    login: Login, block: BaseEvent.() -> Unit = {}
) = BaseEvent("login-updated", login = login).apply(block)