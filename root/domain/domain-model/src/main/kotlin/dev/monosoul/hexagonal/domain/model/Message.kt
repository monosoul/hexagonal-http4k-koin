package dev.monosoul.hexagonal.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Message(val id: MessageId, val body: MessageBody)
