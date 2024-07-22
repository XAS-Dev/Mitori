package xyz.xasmc.mitori.satori.datatype.api.guildMember

import kotlinx.serialization.Serializable

@Serializable
data class GuildMemberList(
    val guild_id: String,
    val next: String? = null,
)