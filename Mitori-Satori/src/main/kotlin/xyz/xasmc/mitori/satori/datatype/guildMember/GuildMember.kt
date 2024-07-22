package xyz.xasmc.mitori.satori.datatype.guildMember

import kotlinx.serialization.Serializable
import xyz.xasmc.mitori.satori.datatype.user.User

@Serializable
data class GuildMember(
    val user: User? = null,      // 用户对象
    val nick: String? = null,    // 用户在群组中的名称
    val avatar: String? = null,  // 用户在群组中的头像
    val joined_at: Long? = null, // 加入时间
)