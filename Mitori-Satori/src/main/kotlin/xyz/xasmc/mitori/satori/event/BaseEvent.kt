package xyz.xasmc.mitori.satori.event

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
open class BaseEvent(
    open val type: String,                // 事件类型
    open val argv: Argv? = null,          // 交互指令
    open val button: Button? = null,      // 交互按钮
    open val channel: Channel? = null,    // 事件所属的频道
    open val guild: Guild? = null,        // 事件所属的群组
    open val login: Login? = null,        // 事件的登录信息
    open val member: GuildMember? = null, // 事件的目标成员
    open val message: Message? = null,    // 事件的消息
    open val operator: User? = null,      // 事件的操作者
    open val role: GuildRole? = null,     // 事件的目标角色
    open val user: User? = null,          // 事件的目标用户
)
