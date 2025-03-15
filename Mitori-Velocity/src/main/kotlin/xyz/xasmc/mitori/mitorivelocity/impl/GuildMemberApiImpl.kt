package xyz.xasmc.mitori.mitorivelocity.impl

import net.kyori.adventure.text.minimessage.MiniMessage
import xyz.xasmc.mitori.mitorivelocity.MitoriVelocity
import xyz.xasmc.mitori.mitorivelocity.util.PlayerUtil
import xyz.xasmc.mitori.satori.api.GuildMemberApiHandler
import xyz.xasmc.mitori.satori.datatype.PagingList
import xyz.xasmc.mitori.satori.datatype.guildMember.GuildMember
import xyz.xasmc.mitori.satori.exception.ApiNotFoundException
import java.util.*

class GuildMemberApiImpl : GuildMemberApiHandler {
    private val plugin = MitoriVelocity.instant
    private val server = plugin.proxyServer
    private val playerJoinTimeMapping = plugin.playerJoinTimeMapping

    override fun guildMemberGet(guildId: String, userId: String): GuildMember {
        if (guildId != "velocity") throw ApiNotFoundException()
        val playerOptional = server.getPlayer(UUID.fromString(userId))
        if (!playerOptional.isPresent) throw ApiNotFoundException()
        val player = playerOptional.get()
        return PlayerUtil.createSatoriMember(
            player,
            playerJoinTimeMapping[player.uniqueId]?.toEpochMilli(),
        )
    }

    override fun guildMemberList(guildId: String, next: String?): PagingList<GuildMember> {
        if (guildId != "velocity") throw ApiNotFoundException()
        val result = mutableListOf<GuildMember>()
        server.allPlayers.forEach {
            val member = PlayerUtil.createSatoriMember(
                it,
                playerJoinTimeMapping[it.uniqueId]?.toEpochMilli(),
            )
            result.add(member)
        }
        return PagingList(result)
    }

    override fun guildMemberKick(guildId: String, userId: String, permanent: Boolean?) {
        if (guildId != "velocity") throw ApiNotFoundException()
        val playerOptional = server.getPlayer(UUID.fromString(userId))
        if (!playerOptional.isPresent) throw ApiNotFoundException()
        val player = playerOptional.get()
        val mm = MiniMessage.miniMessage()
        player.disconnect(mm.deserialize("<red>Kicked by Mitori-Velocity"))
    }
}