package xyz.xasmc.mitori.satori.datatype.api.channel

import kotlinx.serialization.Serializable

@Serializable
data class UserChannelCreate(
    val user_id: String,
    val guild_id: String? = null,
)
