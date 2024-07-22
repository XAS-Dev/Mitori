package xyz.xasmc.mitori.satori.datatype.api.guildMember

import kotlinx.serialization.Serializable

@Serializable
data class GuildMemberMute(
    val guild_id: String,
    val user_id: String,
    val duration: Long,
)
