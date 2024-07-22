package xyz.xasmc.mitori.satori.event.guild

import xyz.xasmc.mitori.satori.datatype.guild.Guild
import xyz.xasmc.mitori.satori.event.BaseEvent

class GuildUpdatedEvent(
    override val guild: Guild,
) : BaseEvent("guild-update")