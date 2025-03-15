package xyz.xasmc.mitori.satori.datatype

import xyz.xasmc.mitori.satori.util.IntEnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = Opcode.Serializer::class)
enum class Opcode(val value: Int) {
    EVENT(0),    // 接收 事件
    PING(1),     // 发送 心跳
    PONG(2),     // 接收 心跳回复
    IDENTIFY(3), // 发送 鉴权
    READY(4),    // 接收 鉴权回复
    META(5);     // 接受 元信息更新

    companion object Serializer : IntEnumSerializer<Opcode>("Op", entries, Opcode::value)
}