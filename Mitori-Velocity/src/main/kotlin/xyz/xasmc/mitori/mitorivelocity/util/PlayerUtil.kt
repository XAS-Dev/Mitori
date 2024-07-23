package xyz.xasmc.mitori.mitorivelocity.util

import com.velocitypowered.api.proxy.Player
import xyz.xasmc.mitori.satori.datatype.guildMember.GuildMember
import xyz.xasmc.mitori.satori.datatype.user.User

object PlayerUtil {
    fun createSatoriMember(player: Player, joinedAt: Long? = null, baseLink: String? = null): GuildMember {
        val avatar = if (baseLink != null) "$baseLink/resource/player/avatar/${player.uniqueId}" else null
        return GuildMember(nick = player.username, avatar = avatar, joined_at = joinedAt)
    }

    fun createSatoriUser(player: Player, baseLink: String? = null): User {
        val avatar = if (baseLink != null) "$baseLink/resource/player/avatar/${player.uniqueId}" else null
        return User(
            id = player.uniqueId.toString(),
            name = player.username,
            nick = player.username,
            avatar = avatar,
            false
        )
    }
}