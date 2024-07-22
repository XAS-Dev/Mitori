package xyz.xasmc.mitori.satori.datatype.channel

import kotlinx.serialization.Serializable

@Serializable
data class Channel(
    val id: String,                // 频道 ID
    val type: ChannelType,         // 频道类型
    val name: String? = null,      // 频道名称
    val parent_id: String? = null, // 父频道 ID
)
