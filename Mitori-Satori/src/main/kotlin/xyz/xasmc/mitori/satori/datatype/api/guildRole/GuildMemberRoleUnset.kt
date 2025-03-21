package xyz.xasmc.mitori.satori.datatype.api.guildRole

import kotlinx.serialization.Serializable

@Serializable
data class GuildMemberRoleUnset(
    val guild_id: String,
    val user_id: String,
    val role_id: String,
)
