package dev.monosoul.hexagonal.domain.impl

import dev.monosoul.hexagonal.domain.api.MessageService
import dev.monosoul.hexagonal.domain.model.Message
import dev.monosoul.hexagonal.domain.model.MessageId
import dev.monosoul.hexagonal.domain.persistence.spi.MessageRepository

class MessageServiceImpl(
    private val messageRepository: MessageRepository
) : MessageService {
    override fun get(id: MessageId): Message = messageRepository.get(id)
}
