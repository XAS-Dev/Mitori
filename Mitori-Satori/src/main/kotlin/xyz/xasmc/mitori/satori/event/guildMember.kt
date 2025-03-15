package xyz.xasmc.mitori.satori.event

import xyz.xasmc.mitori.satori.datatype.guild.Guild
import xyz.xasmc.mitori.satori.datatype.guildMember.GuildMember
import xyz.xasmc.mitori.satori.datatype.user.User

fun guildMemberAddedEvent(
    guild: Guild, member: GuildMember, user: User, block: BaseEvent.() -> Unit = {}
) = BaseEvent("guild-member-added", guild = guild, member = member, user = user).apply(block)

fun guildMemberRemovedEvent(
    guild: Guild, member: GuildMember, user: User, block: BaseEvent.() -> Unit = {}
) = BaseEvent("guild-member-removed", guild = guild, member = member, user = user).apply(block)

fun guildMemberRequestEvent(
    guild: Guild, member: GuildMember, user: User, block: BaseEvent.() -> Unit = {}
) = BaseEvent("guild-member-request", guild = guild, member = member, user = user).apply(block)

fun guildMemberUpdatedEvent(
    guild: Guild, member: GuildMember, user: User, block: BaseEvent.() -> Unit = {}
) = BaseEvent("guild-member-updated", guild = guild, member = member, user = user).apply(block)