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
data class BaseEvent(
    val type: String,                // 事件类型
    var argv: Argv? = null,          // 交互指令
    var button: Button? = null,      // 交互按钮
    var channel: Channel? = null,    // 事件所属的频道
    var guild: Guild? = null,        // 事件所属的群组
    var login: Login? = null,        // 事件的登录信息
    var member: GuildMember? = null, // 事件的目标成员
    var message: Message? = null,    // 事件的消息
    var operator: User? = null,      // 事件的操作者
    var role: GuildRole? = null,     // 事件的目标角色
    var user: User? = null,          // 事件的目标用户
)

