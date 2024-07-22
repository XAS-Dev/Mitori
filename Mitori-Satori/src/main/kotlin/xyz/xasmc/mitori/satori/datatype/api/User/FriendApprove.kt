package xyz.xasmc.mitori.satori.datatype.api.User

import kotlinx.serialization.Serializable

@Serializable
data class FriendApprove(
    val message_id: String,
    val approve: Boolean,
    val comment: String? = null,
)
