package dev.monosoul.hexagonal.domain.thirdparty.spi

import dev.monosoul.hexagonal.domain.model.Message
import dev.monosoul.hexagonal.domain.model.MessageId

interface MessageClient {
    fun get(id: MessageId): Message
}
