package xyz.xasmc.mitori.satori.datatype.signal

import kotlinx.serialization.Serializable

@Serializable
data class Identify(
    val token: String? = null, // 鉴权令牌
    val sequence: Long? = null, // 序列号
)