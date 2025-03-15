package xyz.xasmc.mitori.mitorivelocity

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.player.PlayerChatEvent
import com.velocitypowered.api.event.player.ServerConnectedEvent
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Dependency
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.Logger
import xyz.xasmc.mitori.mitorivelocity.impl.*
import xyz.xasmc.mitori.mitorivelocity.util.AsciiArt
import xyz.xasmc.mitori.mitorivelocity.util.PlayerUtil
import xyz.xasmc.mitori.satori.HandlerConfig
import xyz.xasmc.mitori.satori.SatoriConfig
import xyz.xasmc.mitori.satori.SatoriServer
import xyz.xasmc.mitori.satori.api.GuildRoleApiHandler
import xyz.xasmc.mitori.satori.api.ReactionApiHandler
import xyz.xasmc.mitori.satori.api.UserApiHandler
import xyz.xasmc.mitori.satori.datatype.channel.Channel
import xyz.xasmc.mitori.satori.datatype.channel.ChannelType
import xyz.xasmc.mitori.satori.datatype.guild.Guild
import xyz.xasmc.mitori.satori.datatype.message.Message
import xyz.xasmc.mitori.satori.event.BaseEvent
import xyz.xasmc.mitori.satori.event.guildMemberAddedEvent
import xyz.xasmc.mitori.satori.event.guildMemberRemovedEvent
import xyz.xasmc.mitori.satori.event.messageCreateEvent
import java.time.Instant
import java.util.*

@Plugin(
    id = "mitori-velocity",
    name = "Mitori-Velocity",
    version = BuildConstants.VERSION,
    dependencies = [Dependency(id = "skinsrestorer", optional = true)]
)
class MitoriVelocity @Inject constructor(
    var logger: Logger, var proxyServer: ProxyServer
) {
    companion object {
        lateinit var instant: MitoriVelocity
    }

    private val handlerConfig = HandlerConfig(
        channel = ChannelApiImpl(),
        guild = GuildApiImpl(),
        guildMember = GuildMemberApiImpl(),
        guildRole = object : GuildRoleApiHandler {},
        login = LoginApiImpl(),
        message = MessageApiImpl(),
        reaction = object : ReactionApiHandler {},
        user = object : UserApiHandler {},
    )
    private val resourceHandler = ResourceHandlerImpl()
    private val currentMessageId = 0L

    val satoriConfig = SatoriConfig()
    val satoriServer = SatoriServer(satoriConfig, handlerConfig, resourceHandler)

    val proxyGuild = Guild(
        id = "velocity", name = "Velocity", avatar = satoriServer.makeResourceLink("server-icon.png")
    )
    val playerJoinTimeMapping = mutableMapOf<UUID, Instant>()

    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent) {
        instant = this
        satoriServer.start()
        logger.info("\u001B[34m" + "Mitori-Velocity Initialized!" + "\u001B[0m")
        AsciiArt.logAsciiArt(logger)
        logger.info("\u001B[34m" + "version" + "\u001B[0m: \u001B[32m" + BuildConstants.VERSION + "\u001B[0m")

    }

    @Subscribe
    fun onServerConnected(event: ServerConnectedEvent) {
        val player = event.player
        val playerUuid = player.uniqueId
        val firstJoin = playerJoinTimeMapping[playerUuid] == null
        playerJoinTimeMapping.computeIfAbsent(playerUuid) { Instant.now() }
        if (!firstJoin) return
        // 构建事件
        val now = Instant.now()
        val satoriMember = PlayerUtil.createSatoriMember(player, now.toEpochMilli())
        val satoriUser = PlayerUtil.createSatoriUser(player)
        val satoriEvent = guildMemberAddedEvent(
            guild = proxyGuild,
            member = satoriMember,
            user = satoriUser,
        )
        // 提交事件
        CoroutineScope(Dispatchers.IO).launch {
            satoriServer.emitEvent(satoriEvent)
        }
    }

    @Subscribe
    fun onDisconnect(event: DisconnectEvent) {
        val player = event.player
        val playerUuid = player.uniqueId
        if (!playerJoinTimeMapping.containsKey(playerUuid)) return
        playerJoinTimeMapping.remove(playerUuid)
        // 构建事件
        val now = Instant.now()
        val satoriMember = PlayerUtil.createSatoriMember(player, now.toEpochMilli())
        val satoriUser = PlayerUtil.createSatoriUser(player)
        val satoriEvent = guildMemberRemovedEvent(
            guild = proxyGuild,
            member = satoriMember,
            user = satoriUser,
        )
        // 提交事件
        CoroutineScope(Dispatchers.IO).launch {
            satoriServer.emitEvent(satoriEvent)
        }
    }

    @Subscribe
    fun onPlayerChat(event: PlayerChatEvent) {
        val player = event.player
        val currentServer = player.currentServer
        if (!currentServer.isPresent) return
        val info = currentServer.get().serverInfo
        val serverName = info.name
        val message = event.message
        // 构建事件
        val joinTime = playerJoinTimeMapping[player.uniqueId]
        val satoriChannel = Channel(serverName, ChannelType.TEXT, serverName, "velocity")
        val satoriGuild = proxyGuild
        val satoriUser = PlayerUtil.createSatoriUser(player)
        val satoriMember = PlayerUtil.createSatoriMember(player, joinTime?.toEpochMilli())
        val satoriMessage = Message(
            id = currentMessageId.toString(),
            content = message,
        )
        val baseEvent: BaseEvent = messageCreateEvent(satoriChannel, satoriMessage, satoriUser) {
            guild = satoriGuild
            member = satoriMember
            user = satoriUser
        }
        // 提交事件
        CoroutineScope(Dispatchers.IO).launch {
            satoriServer.emitEvent(baseEvent)
        }
    }
}

