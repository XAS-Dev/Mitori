package xyz.xasmc.mitori.satori.datatype

import kotlinx.serialization.Serializable
import xyz.xasmc.mitori.satori.datatype.channel.Channel
import xyz.xasmc.mitori.satori.datatype.guild.Guild
import xyz.xasmc.mitori.satori.datatype.guildMember.GuildMember
import xyz.xasmc.mitori.satori.datatype.guildRole.GuildRole
import xyz.xasmc.mitori.satori.datatype.interaction.Argv
import xyz.xasmc.mitori.satori.datatype.interaction.Button
import xyz.xasmc.mitori.satori.datatype.login.Login
import xyz.xasmc.mitori.satori.datatype.message.Message
import xyz.xasmc.mitori.satori.datatype.user.User

@Serializable
data class Event(
    val sn: Long,                    // 事件ID
    val type: String,                // 事件类型
    val timestamp: Long,             // 事件的时间戳
    val login: Login? = null,        // 事件的登录信息
    val argv: Argv? = null,          // 交互指令
    val button: Button? = null,      // 交互按钮
    val channel: Channel? = null,    // 事件所属的频道
    val guild: Guild? = null,        // 事件所属的群组
    val member: GuildMember? = null, // 事件的目标成员
    val message: Message? = null,    // 事件的消息
    val operator: User? = null,      // 事件的操作者
    val role: GuildRole? = null,     // 事件的目标角色
    val user: User? = null,          // 事件的目标用户
)
