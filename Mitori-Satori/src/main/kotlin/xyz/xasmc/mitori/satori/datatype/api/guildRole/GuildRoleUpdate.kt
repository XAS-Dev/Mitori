package xyz.xasmc.mitori.satori.datatype.api.guildRole

import kotlinx.serialization.Serializable
import xyz.xasmc.mitori.satori.datatype.guildRole.GuildRole

@Serializable
data class GuildRoleUpdate(
    val guild_id: String,
    val role_id: String,
    val role: GuildRole,
)
