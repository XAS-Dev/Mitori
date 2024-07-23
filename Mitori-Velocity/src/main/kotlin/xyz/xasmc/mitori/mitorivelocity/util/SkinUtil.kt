package xyz.xasmc.mitori.mitorivelocity.util

import com.velocitypowered.api.proxy.Player
import net.skinsrestorer.api.PropertyUtils
import net.skinsrestorer.api.SkinsRestorerProvider
import java.net.HttpURLConnection
import java.net.URL

object SkinUtil {
    private val skinsRestorer = try {
        Class.forName("net.skinsrestorer.api.SkinsRestorerProvider");SkinsRestorerProvider.get()
    } catch (e: Exception) {
        null
    }

    fun getSkin(player: Player): ByteArray {
        if (skinsRestorer == null) return getMojangSkin(player)
        val propertyOptional =
            skinsRestorer.playerStorage.getSkinForPlayer(player.uniqueId, player.username, player.isOnlineMode)
        val link =
            if (propertyOptional.isPresent) PropertyUtils.getSkinTextureUrl(propertyOptional.get())
            else return getMojangSkin(player)
        val url = URL(link)
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"
            return inputStream.readBytes()
        }
    }

    fun getMojangSkin(player: Player): ByteArray {
        TODO("Not Implemented")
        return ByteArray(0)
    }
}