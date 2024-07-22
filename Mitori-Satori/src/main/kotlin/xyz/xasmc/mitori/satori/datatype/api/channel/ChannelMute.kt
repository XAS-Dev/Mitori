package xyz.xasmc.mitori.satori.datatype.api.channel

import kotlinx.serialization.Serializable

@Serializable
data class ChannelMute(
    val channel_id: String,
    val duration: Long,
)