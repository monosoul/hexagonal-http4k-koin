package dev.monosoul.hexagonal.web.impl

import dev.monosoul.hexagonal.domain.api.MessageService
import dev.monosoul.hexagonal.domain.model.MessageBody.MessageBodyWithAdj
import dev.monosoul.hexagonal.domain.model.MessageBody.SimpleMessageBody
import dev.monosoul.hexagonal.domain.model.MessageId
import org.http4k.core.Body
import org.http4k.core.ContentType.Companion.TEXT_PLAIN
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.lens.string
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import java.util.UUID

class MessagesController(
    private val messageService: MessageService
) : RouterProvider {
    private val stringBody = Body.string(contentType = TEXT_PLAIN).toLens()

    override fun invoke() = "messages" bind routes(
        "/{id}" bind GET to { request: Request ->
            request.path("id")!!
                .let(UUID::fromString)
                .let(::MessageId)
                .run(messageService::get)
                .let {
                    when (val body = it.body) {
                        is SimpleMessageBody -> "${body.greeting}, ${body.name}!"
                        is MessageBodyWithAdj -> "${body.greeting}, ${body.adjective} ${body.name}!"
                    }
                }
                .let {
                    Response(OK).with(stringBody of it)
                }
        }
    )
}
