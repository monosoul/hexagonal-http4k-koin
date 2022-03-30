package dev.monosoul.hexagonal.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class MessageBody {
    abstract val greeting: String
    abstract val name: String

    @Serializable
    @SerialName("simple")
    data class SimpleMessageBody(override val greeting: String, override val name: String) : MessageBody()

    @Serializable
    @SerialName("withAdj")
    data class MessageBodyWithAdj(
        override val greeting: String,
        val adjective: String,
        override val name: String,
    ) : MessageBody()
}
