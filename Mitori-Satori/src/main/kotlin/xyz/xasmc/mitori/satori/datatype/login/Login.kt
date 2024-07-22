package xyz.xasmc.mitori.satori.datatype.login

import kotlinx.serialization.Serializable
import xyz.xasmc.mitori.satori.datatype.user.User

@Serializable
data class Login(
    val user: User? = null,               // 用户对象
    val self_id: String? = null,          // 平台账号
    val platform: String? = null,         // 平台名称
    val status: Status,                   // 登录状态
    val features: List<String>? = null,   // 平台特征列表
    val proxy_urls: List<String>? = null, //代理路由列表
)
