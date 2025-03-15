package xyz.xasmc.mitori.satori.datatype.message

import kotlinx.serialization.Serializable
import xyz.xasmc.mitori.satori.datatype.channel.Channel
import xyz.xasmc.mitori.satori.datatype.guild.Guild
import xyz.xasmc.mitori.satori.datatype.guildMember.GuildMember
import xyz.xasmc.mitori.satori.datatype.user.User

@Serializable
data class Message(
    val id: String,                  //消息ID
    val content: String,             // 消息内容
    val channel: Channel? = null,    // 频道对象
    val guild: Guild? = null,        // 群组对象
    val member: GuildMember? = null, // 群组成员对象
    val user: User? = null,          // 用户对象
    val created_at: Long? = null,    // 消息发送的时间戳
    val updated_at: Long? = null,    // 消息修改的时间戳
)
