import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.enums.EnumEntries

// 通用的枚举序列化器
abstract class StringEnumSerializer<T : Enum<T>>(
    private val serialName: String,
    private val values: EnumEntries<T>,
    private val valueToString: (T) -> String
) : KSerializer<T> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: T) {
        encoder.encodeString(valueToString(value))
    }

    override fun deserialize(decoder: Decoder): T {
        val stringVal = decoder.decodeString()
        return values.find { valueToString(it) == stringVal }
            ?: throw IllegalArgumentException("Unknown $serialName value: $stringVal")
    }
}