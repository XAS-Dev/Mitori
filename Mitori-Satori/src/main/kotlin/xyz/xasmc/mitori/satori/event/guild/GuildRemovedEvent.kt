package xyz.xasmc.mitori.satori.event.guild

import xyz.xasmc.mitori.satori.datatype.guild.Guild
import xyz.xasmc.mitori.satori.event.BaseEvent

class GuildRemovedEvent(
    override val guild: Guild,
) : BaseEvent("guild-removed")