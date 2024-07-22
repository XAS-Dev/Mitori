package xyz.xasmc.mitori.satori.datatype.guildRole

import kotlinx.serialization.Serializable

@Serializable
data class GuildRole(
    val id: Int,       // 角色 ID
    val name: String?, // 角色名称
)