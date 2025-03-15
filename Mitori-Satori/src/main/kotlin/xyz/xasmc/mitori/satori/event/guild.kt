package xyz.xasmc.mitori.satori.event

import xyz.xasmc.mitori.satori.datatype.guild.Guild

fun guildAddedEvent(
    guild: Guild, block: BaseEvent.() -> Unit = {}
) = BaseEvent("guild-added", guild = guild).apply(block)

fun guildRemovedEvent(
    guild: Guild, block: BaseEvent.() -> Unit = {}
) = BaseEvent("guild-removed", guild = guild).apply(block)

fun guildRequestEvent(
    guild: Guild, block: BaseEvent.() -> Unit = {}
) = BaseEvent("guild-request", guild = guild).apply(block)

fun guildUpdatedEvent(
    guild: Guild, block: BaseEvent.() -> Unit = {}
) = BaseEvent("guild-update", guild = guild).apply(block)