package xyz.xasmc.mitori.satori.datatype.api.channel

import kotlinx.serialization.Serializable
import xyz.xasmc.mitori.satori.datatype.channel.Channel

@Serializable
data class ChannelUpdate(
    val channel_id: String,
    val data: Channel,
)