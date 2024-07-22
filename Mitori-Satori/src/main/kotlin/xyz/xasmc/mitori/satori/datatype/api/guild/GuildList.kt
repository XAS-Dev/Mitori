package xyz.xasmc.mitori.satori.datatype.api.guild

import kotlinx.serialization.Serializable

@Serializable
data class GuildList(
    val next: String? = null,
)
