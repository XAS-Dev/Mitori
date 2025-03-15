package xyz.xasmc.mitori.satori

import java.util.*

data class SatoriConfig(
    val host: String = "0.0.0.0",
    val port: Int = 3700,
    val path: String? = null,
    val token: String? = null,
    val link: String = "http://$host:$port",
    val platform: String = "mitori",
    val selfId: String = UUID.randomUUID().toString(),
    val selfName: String = "Mitori",
    val selfNick: String = "Mitori"
)
