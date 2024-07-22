package xyz.xasmc.mitori.satori.datatype.api.message

import kotlinx.serialization.Serializable

@Serializable
data class MessageDelete(
    val channel_id: String,
    val message_id: String,
)
