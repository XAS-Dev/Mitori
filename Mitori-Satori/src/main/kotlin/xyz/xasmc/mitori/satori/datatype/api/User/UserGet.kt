package xyz.xasmc.mitori.satori.datatype.api.User

import kotlinx.serialization.Serializable

@Serializable
data class UserGet(
    val user_id: String,
)
