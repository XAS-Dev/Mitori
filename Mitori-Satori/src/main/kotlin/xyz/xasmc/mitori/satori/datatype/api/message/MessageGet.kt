package xyz.xasmc.mitori.satori.datatype.api.message

import kotlinx.serialization.Serializable

@Serializable
data class MessageGet(
    val channel_id: String,
    val message_id: String,
)
