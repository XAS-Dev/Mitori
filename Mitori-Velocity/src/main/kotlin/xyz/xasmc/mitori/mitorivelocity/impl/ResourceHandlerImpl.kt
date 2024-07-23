package xyz.xasmc.mitori.mitorivelocity.impl

import com.velocitypowered.api.proxy.ProxyServer
import xyz.xasmc.mitori.mitorivelocity.util.SkinUtil
import xyz.xasmc.mitori.satori.ResourceHandler
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import javax.imageio.ImageIO

class ResourceHandlerImpl(val server: ProxyServer) : ResourceHandler {
    override fun getResource(id: String): Pair<ByteArray, String>? {
        val playerRegex = Regex("^player/avatar/(.*)$")
        val matchResult = playerRegex.find(id)
        if (matchResult != null) {
            val playerUuid = UUID.fromString(matchResult.groupValues[1])
            val playerOptional = server.getPlayer(playerUuid)
            val player = if (playerOptional.isPresent) playerOptional.get() else return null
            val skin = ImageIO.read(SkinUtil.getSkin(player).inputStream())
            val headInner = skin.getSubimage(8, 8, 8, 8)
            val headOuter = skin.getSubimage(40, 8, 8, 8)
            val painter = BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB)
            val g2d = painter.createGraphics()
            g2d.drawImage(headInner, 256 / 18, 256 / 18, 256 / 9 * 8, 256 / 9 * 8, null)
            g2d.drawImage(headOuter, 0, 0, 256, 256, null)
            g2d.dispose()
            val output = ByteArrayOutputStream()
            ImageIO.write(painter, "png", output)
            return Pair(output.toByteArray(), "skin.png")
        } else if (id == "server-icon.png") {
            val iconFile = File("server-icon.png")
            val data = iconFile.readBytes()
            return Pair(data, "server-icon.png")
        }
        return null
    }
}
