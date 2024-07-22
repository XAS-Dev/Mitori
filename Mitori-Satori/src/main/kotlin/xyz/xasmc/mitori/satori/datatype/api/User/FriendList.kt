package xyz.xasmc.mitori.satori.datatype.api.User

import kotlinx.serialization.Serializable

@Serializable
data class FriendList(
    val next: String? = null,
)
