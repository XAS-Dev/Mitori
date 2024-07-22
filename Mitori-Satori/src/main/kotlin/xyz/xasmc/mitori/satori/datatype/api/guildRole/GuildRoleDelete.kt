package xyz.xasmc.mitori.satori.datatype.api.guildRole

import kotlinx.serialization.Serializable

@Serializable
data class GuildRoleDelete(
    val guild_id: String,
    val role_id: String,
)
