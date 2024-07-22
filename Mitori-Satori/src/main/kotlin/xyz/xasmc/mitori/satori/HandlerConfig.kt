package xyz.xasmc.mitori.satori

import xyz.xasmc.mitori.satori.api.*

data class HandlerConfig(
    val channel: ChannelApiHandler,
    val guild: GuildApiHandler,
    val guildMember: GuildMemberApiHandler,
    val guildRole: GuildRoleApiHandler,
    val login: LoginApiHandler,
    val message: MessageApiHandler,
    val reaction: ReactionApiHandler,
    val user: UserApiHandler,
)
