package dev.monosoul.hexagonal.domain.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.UUID
import java.util.UUID.fromString
import java.util.UUID.randomUUID

@Serializable(with = MessageIdSerializer::class)
@JvmInline
value class MessageId(val uuid: UUID = randomUUID())

private object MessageIdSerializer : KSerializer<MessageId> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("MessageId", STRING)
    override fun deserialize(decoder: Decoder): MessageId = MessageId(fromString(decoder.decodeString()))
    override fun serialize(encoder: Encoder, value: MessageId) = encoder.encodeString(value.uuid.toString())
}
