package xyz.xasmc.mitori.mitorivelocity.util

import com.velocitypowered.api.proxy.Player
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import xyz.xasmc.mitori.mitorivelocity.MitoriVelocity
import xyz.xasmc.mitori.satori.util.xmlParser.PlainTextElement
import xyz.xasmc.mitori.satori.util.xmlParser.XmlDocument
import xyz.xasmc.mitori.satori.util.xmlParser.XmlElement
import java.util.*
import kotlin.jvm.optionals.getOrNull

object MessageUtil {
    private val mm = MiniMessage.miniMessage()
    private val server = MitoriVelocity.instant.proxyServer

    data class Context(
        val currentPlayer: Player
    )

    private fun parseText(element: XmlElement): Component = Component.text((element as PlainTextElement).text)

    private fun parseAt(element: XmlElement, context: Context): Component {
        val type = element.attributes["type"]
        val id = element.attributes["id"]
        val name = element.attributes["name"]
        val role = element.attributes["role"]

        when {
            type == "all" || type == "here" -> return mm.deserialize("<yellow>@$type</yellow>")
            id != null || name != null -> {
                val playerOpt = id?.let { server.getPlayer(UUID.fromString(it)) } ?: name?.let { server.getPlayer(it) }
                if (playerOpt == null) return Component.empty()
                val player = playerOpt.getOrNull() ?: return Component.empty()
                return if (player == context.currentPlayer) mm.deserialize("<yellow>@${player.username}</yellow>")
                else mm.deserialize("@${player.username}")
            }

            role != null -> return Component.empty() // TODO
            else -> return Component.empty()
        }
    }

    private fun parseSharp(element: XmlElement): Component {
        val id = element.attributes["id"] ?: element.attributes["name"] ?: return Component.empty()
        val serverOptional = server.getServer(id)
        if (!serverOptional.isPresent) return Component.empty()
        val channelServer = serverOptional.get()
        val serverName = channelServer.serverInfo.name
        return mm.deserialize("<green><hover:show_text:'<green>Join to ${serverName}'><click:run_command:'/server ${serverName}'>")
    }

    private fun parseLink(element: XmlElement, context: Context): Component {
        val link = element.attributes["href"] ?: ""
        val result = Component.empty().color(NamedTextColor.GREEN).clickEvent(ClickEvent.openUrl(link))
            .hoverEvent(HoverEvent.showText(mm.deserialize("<green>click to open link $link</green>")))
        element.children.forEach {
            result.append(parseElement(it, context))
        }
        return result
    }

    private fun parseElement(element: XmlElement, context: Context): Component {
        // TODO: implement this
        return when (element.name) {
            "text" -> parseText(element as PlainTextElement)
            "at" -> parseAt(element, context)
            "sharp" -> parseSharp(element)
            "a" -> parseLink(element, context)
            else -> Component.empty()
        }
    }

    private fun sendToPlayer(content: String, player: Player) {
        val doc = XmlDocument.fromString(content)
        val component = Component.empty()
        doc.elements.forEach {
            component.append(parseElement(it, Context(player)))
        }
        player.sendMessage(component)
    }

    fun sendSatoriMessage(channelId: String, content: String) {
        val regServer = server.getServer(channelId).getOrNull() ?: return
        regServer.playersConnected.forEach { player ->
            sendToPlayer(content, player)
        }
    }
}