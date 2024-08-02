package xyz.xasmc.mitori.mitorivelocity.util

import com.velocitypowered.api.proxy.Player
import xyz.xasmc.mitori.satori.datatype.guildMember.GuildMember
import xyz.xasmc.mitori.satori.datatype.user.User
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

object PlayerUtil {
    fun createSatoriMember(player: Player, joinedAt: Long? = null, baseLink: String? = null): GuildMember {
        val avatar = if (baseLink != null) "$baseLink/resource/player/avatar/${player.uniqueId}" else null
        return GuildMember(nick = player.username, avatar = avatar, joined_at = joinedAt)
    }

    fun createSatoriUser(player: Player, baseLink: String? = null): User {
        val avatar = if (baseLink != null) "$baseLink/resource/player/avatar/${player.uniqueId}" else null
        return User(
            id = player.uniqueId.toString(), name = player.username, nick = player.username, avatar = avatar, false
        )
    }

    fun generateAvatar(player: Player): ByteArray {
        val skin = ImageIO.read(SkinUtil.getSkin(player).inputStream())
        val headInner = skin.getSubimage(8, 8, 8, 8)
        val headOuter = skin.getSubimage(40, 8, 8, 8)
        val painter = BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB)
        val g2d = painter.createGraphics()
        g2d.drawImage(headInner, 256 / 18, 256 / 18, 256 - 256 / 9, 256 - 256 / 9, null)
        g2d.drawImage(headOuter, 0, 0, 256, 256, null)
        g2d.dispose()
        val output = ByteArrayOutputStream()
        ImageIO.write(painter, "png", output)
        return output.toByteArray()
    }
}