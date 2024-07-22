package xyz.xasmc.mitori.satori.datatype.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,              // 用户 ID
    val name: String? = null,    // 用户名称
    val nick: String? = null,    // 用户昵称
    val avatar: String? = null,  // 用户头像
    val is_bot: Boolean? = null, // 是否为机器人
)
