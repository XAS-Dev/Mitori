package xyz.xasmc.mitori.satori.datatype.interaction

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class Argv(
    val name: String,                // 指令名称
    val argument: List<JsonElement>, // 参数
    val options: JsonObject,         // 选项
)
