package xyz.xasmc.mitori.mitorivelocity.util

import com.google.gson.JsonParser
import com.velocitypowered.api.proxy.Player
import net.skinsrestorer.api.PropertyUtils
import net.skinsrestorer.api.SkinsRestorerProvider
import xyz.xasmc.mitori.mitorivelocity.MitoriVelocity
import java.net.HttpURLConnection
import java.net.URI
import java.util.*

object SkinUtil {
    val logger = MitoriVelocity.instant.logger

    private val DEFAULT_SKINS = listOf(
        "slim/alex",
        "slim/ari",
        "slim/efe",
        "slim/kai",
        "slim/makena",
        "slim/noor",
        "slim/steve",
        "slim/sunny",
        "slim/zuri",
        "wide/alex",
        "wide/ari",
        "wide/efe",
        "wide/kai",
        "wide/makena",
        "wide/noor",
        "wide/steve",
        "wide/sunny",
        "wide/zuri"
    )

    private val skinsRestorer =
        kotlin.runCatching { Class.forName("net.skinsrestorer.api.SkinsRestorerProvider");SkinsRestorerProvider.get() }
            .getOrNull()

    fun getSkin(player: Player): ByteArray {
        if (skinsRestorer != null) return getSrSkin(player)
        if (player.isOnlineMode) return getMojangSkin(player)
        return getDefaultSkin(player)
    }


    private fun getSrSkin(player: Player): ByteArray {
        skinsRestorer ?: throw NullPointerException("skinsRestorer")
        val uniqueId = player.uniqueId
        val username = player.username
        val isOnlineMode = player.isOnlineMode
        val propertyOptional = skinsRestorer.playerStorage.getSkinForPlayer(uniqueId, username, isOnlineMode)
        if (!propertyOptional.isPresent) return fallbackSkin(player, "sr property not present")
        val link = PropertyUtils.getSkinTextureUrl(propertyOptional.get())
        val url = URI(link).toURL()
        try {
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                return inputStream.readBytes()
            }
        } catch (e: Exception) {
            return fallbackSkin(player, "can't get ${url}; ${e}")
        }
    }

    private fun getMojangSkin(player: Player): ByteArray {
        val uuid = player.uniqueId.toString().replace("-", "")
        val apiUrl = "https://sessionserver.mojang.com/session/minecraft/profile/$uuid"
        val profileUrl = URI(apiUrl).toURL()
        val textureUrlStr = try {
            with(profileUrl.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                val response = inputStream.bufferedReader().readText()
                if (response.isEmpty()) return fallbackSkin(player, "no such player")
                val json = JsonParser.parseString(response).asJsonObject
                val textures = json.getAsJsonArray("properties")?.firstOrNull {
                    it.asJsonObject.get("name").asString == "textures"
                }?.asJsonObject?.get("value")?.asString ?: return fallbackSkin(player, "bad response")
                val decoded = runCatching { Base64.getDecoder().decode(textures) }.getOrNull() ?: return fallbackSkin(
                    player,
                    "can't decode textures data"
                )
                val textureUrl = JsonParser.parseString(decoded.toString()).asJsonObject.getAsJsonObject("textures")
                    .getAsJsonObject("SKIN").get("url")?.asString ?: return fallbackSkin(player, "bad textures data")
                return@with textureUrl
            }
        } catch (e: Exception) {
            return fallbackSkin(player, "can't get ${profileUrl}; ${e}")
        }

        val textureUrl = URI(textureUrlStr).toURL()
        try {
            with(textureUrl.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                return inputStream.readAllBytes()
            }
        } catch (e: Exception) {
            return fallbackSkin(player, "can't get ${textureUrl}; ${e}")
        }
    }

    private fun getDefaultSkin(player: Player): ByteArray {
        val index = calculateSkinIndex(player.uniqueId)
        val skin = DEFAULT_SKINS[index]
        val data =
            this::class.java.getResourceAsStream("skins/${skin}.png") ?: throw RuntimeException("Can't read skin file.")
        return data.readAllBytes()
    }

    private fun calculateSkinIndex(uuid: UUID): Int {
        val mostSigBits = uuid.mostSignificantBits
        val leastSigBits = uuid.leastSignificantBits
        val combined = mostSigBits xor leastSigBits
        return (combined xor (combined shr 32)).and(0xFFFFFFFF).mod(18)
    }

    private fun fallbackSkin(player: Player, reason: String): ByteArray {
        logger.warn("Can't get skin, using fallback skin, reason: ${reason}")
        return getDefaultSkin(player)
    }
}