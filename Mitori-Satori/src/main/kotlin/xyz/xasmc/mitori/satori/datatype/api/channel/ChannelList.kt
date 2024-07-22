package xyz.xasmc.mitori.satori.datatype.api.channel

import kotlinx.serialization.Serializable

@Serializable
data class ChannelList(
    val guild_id: String,
    val next: String? = null,
)
