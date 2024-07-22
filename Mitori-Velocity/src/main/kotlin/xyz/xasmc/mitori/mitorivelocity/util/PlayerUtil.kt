package xyz.xasmc.mitori.mitorivelocity.util

import com.velocitypowered.api.proxy.Player
import xyz.xasmc.mitori.satori.datatype.guildMember.GuildMember
import xyz.xasmc.mitori.satori.datatype.user.User

object PlayerUtil {
    fun createSatoriMember(player: Player, joinedAt: Long?): GuildMember {
        return GuildMember(nick = player.username, avatar = "玩家头像", joined_at = joinedAt)
    }

    fun createSatoriUser(player: Player): User {
        return User(
            id = player.uniqueId.toString(), name = player.username, nick = player.username, avatar = "玩家头像", false
        )
    }
}