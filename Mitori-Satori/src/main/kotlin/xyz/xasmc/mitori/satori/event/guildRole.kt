package xyz.xasmc.mitori.satori.event

import xyz.xasmc.mitori.satori.datatype.guild.Guild
import xyz.xasmc.mitori.satori.datatype.guildRole.GuildRole

fun guildRoleCreatedEvent(
    guild: Guild, role: GuildRole, block: BaseEvent.() -> Unit = {}
) = BaseEvent("guild-role-created", guild = guild, role = role).apply(block)


fun guildRoleDeletedEvent(
    guild: Guild, role: GuildRole, block: BaseEvent.() -> Unit = {}
) = BaseEvent("guild-role-deleted", guild = guild, role = role).apply(block)

fun guildRoleUpdatedEvent(
    guild: Guild, role: GuildRole, block: BaseEvent.() -> Unit = {}
) = BaseEvent("guild-role-updated", guild = guild, role = role).apply(block)