package xyz.xasmc.mitori.satori.datatype.api.message

import kotlinx.serialization.Serializable

@Serializable
data class MessageCreate(
    val channel_id: String,
    val content: String,
)
