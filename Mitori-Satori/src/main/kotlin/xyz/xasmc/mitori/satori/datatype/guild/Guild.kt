package xyz.xasmc.mitori.satori.datatype.guild

import kotlinx.serialization.Serializable

@Serializable
data class Guild(
    val id: String,             // 群组 ID
    val name: String? = null,   // 群组名称
    val avatar: String? = null, // 群组头像
)
