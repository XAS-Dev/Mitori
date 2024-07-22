package xyz.xasmc.mitori.satori.datatype.api.guildRole

import kotlinx.serialization.Serializable
import xyz.xasmc.mitori.satori.datatype.guildRole.GuildRole

@Serializable
data class GuildRoleCreate(
    val guild_id: String,
    val role: GuildRole,
)
