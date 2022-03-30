package dev.monosoul.hexagonal.domain.persistence.spi

import dev.monosoul.hexagonal.domain.model.Message
import dev.monosoul.hexagonal.domain.model.MessageId

interface MessageRepository {
    fun save(message: Message): Message
    fun get(id: MessageId): Message
}
