package xyz.xasmc.mitori.satori.event.guildRole

import xyz.xasmc.mitori.satori.datatype.guild.Guild
import xyz.xasmc.mitori.satori.datatype.guildRole.GuildRole
import xyz.xasmc.mitori.satori.event.BaseEvent

class GuildRoleUpdatedEvent(
    override val guild: Guild,
    override val role: GuildRole,
) : BaseEvent("guild-role-updated")