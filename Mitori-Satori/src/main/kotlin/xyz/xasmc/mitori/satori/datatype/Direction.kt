package xyz.xasmc.mitori.satori.datatype

import StringEnumSerializer
import kotlinx.serialization.Serializable


@Serializable(with = Direction.Serializer::class)
enum class Direction(val value: String) {
    BEFORE("before"),
    AFTER("after"),
    AROUND("around");

    companion object Serializer : StringEnumSerializer<Direction>("Direction", entries, Direction::value)
}