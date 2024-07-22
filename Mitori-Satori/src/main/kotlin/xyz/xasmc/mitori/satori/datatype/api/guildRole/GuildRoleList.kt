package xyz.xasmc.mitori.satori.datatype.api.guildRole

import kotlinx.serialization.Serializable

@Serializable
data class GuildRoleList(
    val guild_id: String,
    val next: String? = null,
)
