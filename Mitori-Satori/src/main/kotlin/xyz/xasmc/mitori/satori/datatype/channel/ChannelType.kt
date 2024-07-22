package xyz.xasmc.mitori.satori.datatype.channel

import IntEnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ChannelType.Serializer::class)
enum class ChannelType(val value: Int) {
    TEXT(0),     // 文本频道
    DIRECT(1),   // 私聊频道
    CATEGORY(2), // 分类频道
    VOICE(3);    // 语音频道

    companion object Serializer : IntEnumSerializer<ChannelType>("ChannelType", entries, ChannelType::value)
}
