package xyz.xasmc.mitori.satori.datatype.api.guild

import kotlinx.serialization.Serializable

@Serializable
data class GuildGet(
    val guild_id: String,
)
