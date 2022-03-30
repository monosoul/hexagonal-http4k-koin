package dev.monosoul.hexagonal.domain.api

import dev.monosoul.hexagonal.domain.model.Message
import dev.monosoul.hexagonal.domain.model.MessageId

interface MessageService {
    fun get(id: MessageId): Message
}
