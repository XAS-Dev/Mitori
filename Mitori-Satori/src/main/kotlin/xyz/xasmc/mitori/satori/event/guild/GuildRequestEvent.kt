package xyz.xasmc.mitori.satori.event.guild

import xyz.xasmc.mitori.satori.datatype.guild.Guild
import xyz.xasmc.mitori.satori.event.BaseEvent

class GuildRequestEvent(
    override val guild: Guild,
) : BaseEvent("guild-request")