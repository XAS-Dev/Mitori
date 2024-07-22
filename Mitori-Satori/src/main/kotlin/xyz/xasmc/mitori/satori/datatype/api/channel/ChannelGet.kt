package xyz.xasmc.mitori.satori.datatype.api.channel

import kotlinx.serialization.Serializable

@Serializable
data class ChannelGet(
    val channel_id: String,
)
