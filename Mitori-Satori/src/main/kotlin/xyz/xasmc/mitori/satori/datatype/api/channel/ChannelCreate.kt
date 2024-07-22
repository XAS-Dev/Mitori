package xyz.xasmc.mitori.satori.datatype.api.channel

import kotlinx.serialization.Serializable
import xyz.xasmc.mitori.satori.datatype.channel.Channel

@Serializable
data class ChannelCreate(
    val guild_id: String,
    val data: Channel,
)