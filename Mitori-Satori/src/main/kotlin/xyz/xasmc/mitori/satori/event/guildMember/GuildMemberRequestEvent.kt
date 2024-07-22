package xyz.xasmc.mitori.satori.event.guildMember

import xyz.xasmc.mitori.satori.datatype.guild.Guild
import xyz.xasmc.mitori.satori.datatype.guildMember.GuildMember
import xyz.xasmc.mitori.satori.datatype.user.User
import xyz.xasmc.mitori.satori.event.BaseEvent

class GuildMemberRequestEvent(
    override val guild: Guild,
    override val member: GuildMember,
    override val user: User,
) : BaseEvent("guild-member-request")