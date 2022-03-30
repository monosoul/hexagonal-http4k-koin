package dev.monosoul.hexagonal.persistence.jooq.impl

import dev.monosoul.hexagonal.domain.model.Message
import dev.monosoul.hexagonal.domain.model.MessageId
import dev.monosoul.hexagonal.domain.persistence.spi.MessageRepository
import dev.monosoul.hexagonal.persistence.jooq.generated.Tables.MESSAGES
import dev.monosoul.hexagonal.persistence.jooq.generated.tables.records.MessagesRecord
import org.jooq.DSLContext

class MessageRepositoryImpl(
    private val jooq: DSLContext
) : MessageRepository {
    override fun save(message: Message) = jooq.insertInto(MESSAGES)
        .set(
            MessagesRecord().apply {
                id = message.id
                body = message.body
            }
        )
        .returning()
        .fetchOne(::toMessage)!!

    override fun find(id: MessageId) = jooq.selectFrom(MESSAGES)
        .where(MESSAGES.ID.eq(id))
        .fetchOne(::toMessage)

    private fun toMessage(record: MessagesRecord) = with(record) { Message(id, body) }
}
