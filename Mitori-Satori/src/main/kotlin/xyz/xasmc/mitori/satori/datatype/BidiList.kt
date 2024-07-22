package xyz.xasmc.mitori.satori.datatype

import kotlinx.serialization.Serializable

@Serializable
data class BidiList<T>(
    val data: List<T>,
    val prev: String? = null,
    val next: String? = null,
)
