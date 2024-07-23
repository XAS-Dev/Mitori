package xyz.xasmc.mitori.mitorivelocity

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.player.PlayerChatEvent
import com.velocitypowered.api.event.player.ServerConnectedEvent
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import org.slf4j.Logger
import xyz.xasmc.mitori.mitorivelocity.impl.ResourceHandlerImpl
import xyz.xasmc.mitori.mitorivelocity.util.AsciiArt
import xyz.xasmc.mitori.mitorivelocity.util.PlayerUtil
import xyz.xasmc.mitori.satori.HandlerConfig
import xyz.xasmc.mitori.satori.MitoriSatori
import xyz.xasmc.mitori.satori.SatoriConfig
import xyz.xasmc.mitori.satori.api.*
import xyz.xasmc.mitori.satori.datatype.channel.Channel
import xyz.xasmc.mitori.satori.datatype.channel.ChannelType
import xyz.xasmc.mitori.satori.datatype.guild.Guild
import xyz.xasmc.mitori.satori.datatype.message.Message
import xyz.xasmc.mitori.satori.event.BaseEvent
import xyz.xasmc.mitori.satori.event.guildMember.GuildMemberAddedEvent
import xyz.xasmc.mitori.satori.event.guildMember.GuildMemberRemovedEvent
import xyz.xasmc.mitori.satori.event.message.MessageCreateEvent
import xyz.xasmc.mitorivelocity.BuildConstants
import java.time.Instant
import java.util.*

@Plugin(id = "mitori-velocity", name = "Mitori-Velocity", version = BuildConstants.VERSION)
class MitoriVelocity {
    @Inject
    private lateinit var logger: Logger
    private val satoriConfig = SatoriConfig()
    private val handlerConfig = HandlerConfig(
        channel = object : ChannelApiHandler {},
        guild = object : GuildApiHandler {},
        guildMember = object : GuildMemberApiHandler {},
        guildRole = object : GuildRoleApiHandler {},
        login = object : LoginApiHandler {},
        message = object : MessageApiHandler {},
        reaction = object : ReactionApiHandler {},
        user = object : UserApiHandler {},
    )
    private val resourceHandler = ResourceHandlerImpl()
    private val satoriServer = MitoriSatori(satoriConfig, handlerConfig, resourceHandler)
    private val currentMessageId = 0L

    private val proxyGuild = Guild(
        id = "velocity", name = "Velocity", avatar = satoriServer.baseLink + "/resource/server-icon.png"
    )
    private val playerJoinTime = mutableMapOf<UUID, Instant>()

    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent) {
        satoriServer.start()
        logger.info("\u001B[34m" + "Mitori-Velocity Initialized!" + "\u001B[0m")
        AsciiArt.logAsciiArt(logger)
        logger.info("\u001B[34m" + "version" + "\u001B[0m: \u001B[32m" + BuildConstants.VERSION + "\u001B[0m")

    }

    @Subscribe
    fun onServerConnected(event: ServerConnectedEvent) {
        val player = event.player
        val playerUuid = player.uniqueId
        val firstJoin = playerJoinTime[playerUuid] == null
        playerJoinTime.computeIfAbsent(playerUuid) { Instant.now() }
        if (!firstJoin) return
        // 构建事件
        val now = Instant.now()
        val satoriMember = PlayerUtil.createSatoriMember(player, now.toEpochMilli())
        val satoriUser = PlayerUtil.createSatoriUser(player)
        val satoriEvent = GuildMemberAddedEvent(
            guild = proxyGuild,
            member = satoriMember,
            user = satoriUser,
        )
        // 提交事件
        satoriServer.emit(satoriEvent)
    }

    @Subscribe
    fun onDisconnect(event: DisconnectEvent) {
        val player = event.player
        val playerUuid = player.uniqueId
        if (!playerJoinTime.containsKey(playerUuid)) return
        playerJoinTime.remove(playerUuid)
        // 构建事件
        val now = Instant.now()
        val satoriMember = PlayerUtil.createSatoriMember(player, now.toEpochMilli())
        val satoriUser = PlayerUtil.createSatoriUser(player)
        val satoriEvent = GuildMemberRemovedEvent(
            guild = proxyGuild,
            member = satoriMember,
            user = satoriUser,
        )
        // 提交事件
        satoriServer.emit(satoriEvent)
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
        val satoriChannel = Channel(serverName, ChannelType.TEXT, serverName)
        val satoriUser = PlayerUtil.createSatoriUser(player)
        val satoriMember = PlayerUtil.createSatoriMember(player, playerJoinTime[player.uniqueId]?.toEpochMilli())
        val satoriMessage = Message(
            id = currentMessageId.toString(),
            content = message,
            channel = satoriChannel,
            guild = proxyGuild,
            member = satoriMember,
            user = satoriUser,
            created_at = Instant.now().toEpochMilli(),
        )
        val satoriEvent: BaseEvent = MessageCreateEvent(satoriChannel, satoriMessage, satoriUser)
        // 提交事件
        satoriServer.emit(satoriEvent)
    }
}

