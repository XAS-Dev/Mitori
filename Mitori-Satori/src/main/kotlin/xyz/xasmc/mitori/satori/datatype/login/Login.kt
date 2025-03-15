package xyz.xasmc.mitori.satori.datatype.login

import kotlinx.serialization.Serializable
import xyz.xasmc.mitori.satori.datatype.user.User

@Serializable
data class Login(
    val sn: Long,                       // 序列号
    val platform: String? = null,       // 平台名称
    val user: User? = null,             // 用户对象
    val status: Status,                 // 登录状态
    val adapter: String,                // 适配器名称
    val features: List<String>? = null, // 平台特征列表
)
