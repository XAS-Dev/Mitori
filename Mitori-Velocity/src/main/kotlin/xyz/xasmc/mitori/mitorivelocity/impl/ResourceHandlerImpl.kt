package xyz.xasmc.mitori.mitorivelocity.impl

import com.velocitypowered.api.proxy.ProxyServer
import xyz.xasmc.mitori.mitorivelocity.util.PlayerUtil
import xyz.xasmc.mitori.satori.ResourceHandler
import java.io.File
import java.util.*

class ResourceHandlerImpl(val server: ProxyServer) : ResourceHandler {
    override fun getResource(id: String): Pair<ByteArray, String>? {
        val playerRegex = Regex("^player/avatar/(.*)$")
        val matchResult = playerRegex.find(id)
        if (matchResult != null) {
            val playerUuid = UUID.fromString(matchResult.groupValues[1])
            val playerOptional = server.getPlayer(playerUuid)
            val player = if (playerOptional.isPresent) playerOptional.get() else return null
            return Pair(PlayerUtil.generateAvatar(player), "skin.png")
        } else if (id == "server-icon.png") {
            val iconFile = File("server-icon.png")
            val data = iconFile.readBytes()
            return Pair(data, "server-icon.png")
        }
        return null
    }
}
