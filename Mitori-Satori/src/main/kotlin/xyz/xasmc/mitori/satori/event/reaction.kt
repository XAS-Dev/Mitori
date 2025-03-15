package xyz.xasmc.mitori.satori.event

fun reactionAddedEvent(
    block: BaseEvent.() -> Unit = {}
) = BaseEvent("reaction-added").apply(block)

fun reactionRemovedEvent(
    block: BaseEvent.() -> Unit = {}
) = BaseEvent("reaction-removed").apply(block)