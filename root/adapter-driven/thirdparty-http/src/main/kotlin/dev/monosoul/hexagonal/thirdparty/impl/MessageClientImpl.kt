package dev.monosoul.hexagonal.thirdparty.impl

import dev.monosoul.hexagonal.domain.model.Message
import dev.monosoul.hexagonal.domain.model.MessageBody
import dev.monosoul.hexagonal.domain.model.MessageId
import dev.monosoul.hexagonal.domain.thirdparty.spi.MessageClient
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.UriTemplate
import org.http4k.format.KotlinxSerialization.auto

class MessageClientImpl(
    private val client: HttpHandler,
    clientConfig: ThirdPartyClientConfig,
) : MessageClient {
    private val messageLens = Body.auto<MessageBody>().toLens()
    private val urlTemplate = clientConfig.toUriTemplate("/messages/{id}")

    override fun get(id: MessageId): Message = Request(GET, urlTemplate.generate(mapOf("id" to id.uuid.toString())))
        .run(client)
        .let(messageLens)
        .let {
            Message(id, it)
        }

    private fun ThirdPartyClientConfig.toUriTemplate(path: String) = UriTemplate.from("$protocol://$host:$port$path")
}
