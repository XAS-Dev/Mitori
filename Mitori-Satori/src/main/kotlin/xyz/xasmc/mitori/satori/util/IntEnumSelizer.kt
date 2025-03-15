package xyz.xasmc.mitori.satori.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.enums.EnumEntries

// 通用的枚举序列化器
abstract class IntEnumSerializer<T : Enum<T>>(
    private val serialName: String,
    private val values: EnumEntries<T>,
    private val valueToInt: (T) -> Int
) : KSerializer<T> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: T) {
        encoder.encodeInt(valueToInt(value))
    }

    override fun deserialize(decoder: Decoder): T {
        val intVal = decoder.decodeInt()
        return values.find { valueToInt(it) == intVal }
            ?: throw IllegalArgumentException("Unknown $serialName value: $intVal")
    }
}