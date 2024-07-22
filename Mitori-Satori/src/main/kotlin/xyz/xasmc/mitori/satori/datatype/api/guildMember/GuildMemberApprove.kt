package xyz.xasmc.mitori.satori.datatype.api.guildMember

import kotlinx.serialization.Serializable

@Serializable
data class GuildMemberApprove(
    val message_id: String,
    val approve: Boolean,
    val comment: String? = null,
)