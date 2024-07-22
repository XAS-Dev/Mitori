package xyz.xasmc.mitori.satori.datatype.signal

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import xyz.xasmc.mitori.satori.datatype.Opcode

@Serializable
open class Payload(
    val op: Opcode,               // 信令类型
    val body: JsonObject? = null, // 信令数据
)