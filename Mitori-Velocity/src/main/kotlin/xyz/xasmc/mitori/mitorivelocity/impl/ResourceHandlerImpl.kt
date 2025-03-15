package xyz.xasmc.mitori.mitorivelocity.impl

import xyz.xasmc.mitori.mitorivelocity.MitoriVelocity
import xyz.xasmc.mitori.mitorivelocity.util.PlayerUtil
import xyz.xasmc.mitori.satori.ResourceHandler
import xyz.xasmc.mitori.satori.util.URI
import java.io.File
import java.util.*

class ResourceHandlerImpl : ResourceHandler {
    private val plugin = MitoriVelocity.instant
    private val server = plugin.proxyServer

    override fun getResource(id: String): ResourceHandler.Resource? {
        val uri = URI(id)
        return when {
            uri.parent.equals("player/avatar/") -> {
                val playerUuid = UUID.fromString(uri.name)
                val playerOptional = server.getPlayer(playerUuid)
                val player = if (playerOptional.isPresent) playerOptional.get() else return null
                return ResourceHandler.Resource(PlayerUtil.generateAvatar(player), extension = ".png")
            }

            uri.equals("server-icon.png") -> {
                val iconFile = File("server-icon.png")
                val data = iconFile.readBytes()
                ResourceHandler.Resource(data, extension = ".png")
            }

            else -> null
        }
    }
}
