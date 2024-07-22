package xyz.xasmc.mitori.satori.datatype.api.guild

import kotlinx.serialization.Serializable

@Serializable
data class GuildApprove(
    val message_id: String,
    val approve: Boolean,
    val comment: String?,
)