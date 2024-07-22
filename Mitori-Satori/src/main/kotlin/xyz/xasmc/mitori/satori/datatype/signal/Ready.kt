package xyz.xasmc.mitori.satori.datatype.signal

import kotlinx.serialization.Serializable
import xyz.xasmc.mitori.satori.datatype.login.Login

@Serializable
data class Ready(
    val logins: List<Login>, // 登录信息
)