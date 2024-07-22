package xyz.xasmc.mitori.satori.datatype.api.message

import xyz.xasmc.mitori.satori.datatype.Direction
import xyz.xasmc.mitori.satori.datatype.Order

data class MessageList(
    val channel_id: String,
    val next: String? = null,
    val direction: Direction? = null,
    val limit: Long? = null,
    val order: Order? = null
)
