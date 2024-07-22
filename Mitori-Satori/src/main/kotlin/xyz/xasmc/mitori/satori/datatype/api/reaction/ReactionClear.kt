package xyz.xasmc.mitori.satori.datatype.api.reaction

import kotlinx.serialization.Serializable

@Serializable
data class ReactionClear(
    val channel_id: String,
    val message_id: String,
    val emoji: String,
)
