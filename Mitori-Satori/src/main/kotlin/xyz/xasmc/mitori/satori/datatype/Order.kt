package xyz.xasmc.mitori.satori.datatype

import StringEnumSerializer
import kotlinx.serialization.Serializable


@Serializable(with = Order.Serializer::class)
enum class Order(val value: String) {
    ASC("asc"),
    DESC("desc");

    companion object Serializer : StringEnumSerializer<Order>("Order", entries, Order::value)
}