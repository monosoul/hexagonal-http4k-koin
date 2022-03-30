package dev.monosoul.hexagonal.thirdparty.impl

import dev.monosoul.hexagonal.domain.model.Message
import dev.monosoul.hexagonal.domain.model.MessageBody
import dev.monosoul.hexagonal.domain.model.MessageId
import dev.monosoul.hexagonal.domain.thirdparty.spi.MessageClient
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.format.KotlinxSerialization.auto

class MessageClientImpl(
    private val client: HttpHandler
) : MessageClient {
    private val messageLens = Body.auto<MessageBody>().toLens()

    override fun get(id: MessageId): Message = Request(GET, "http://localhost:9966/messages/${id.uuid}")
        .run(client)
        .let(messageLens)
        .let {
            Message(id, it)
        }
}
