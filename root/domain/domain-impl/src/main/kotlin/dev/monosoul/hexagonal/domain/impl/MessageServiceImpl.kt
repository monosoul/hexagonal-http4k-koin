package dev.monosoul.hexagonal.domain.impl

import dev.monosoul.hexagonal.domain.api.MessageService
import dev.monosoul.hexagonal.domain.model.Message
import dev.monosoul.hexagonal.domain.model.MessageId
import dev.monosoul.hexagonal.domain.persistence.spi.MessageRepository
import dev.monosoul.hexagonal.domain.thirdparty.spi.MessageClient

class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val messageClient: MessageClient,
) : MessageService {
    override fun get(id: MessageId): Message = messageRepository.find(id)
        ?: messageClient.get(id).also(messageRepository::save)
}
