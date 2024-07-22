package xyz.xasmc.mitori.satori.datatype

import kotlinx.serialization.Serializable

@Serializable
data class PagingList<T>(
    val data: List<T>,
    val next: String? = null,
)
