package dev.monosoul.hexagonal.web.impl

import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.Undertow
import org.http4k.server.asServer
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.koin.dsl.onClose

fun webImplModule(config: WebConfig, autostart: Boolean = true) = module {
    singleController {
        MessagesController(get())
    }
    single<Http4kServer> {
        routes(*getAll<RoutingHttpHandler>().toTypedArray()).asServer(Undertow(config.port))
    }.onClose {
        it?.stop()
    }

    single(qualifier = named("webInitializer"), createdAtStart = autostart) {
        Runnable { get<Http4kServer>().start() }.also { it.run() }
    }
}

internal fun interface RouterProvider : () -> RoutingHttpHandler {
    override fun invoke(): RoutingHttpHandler
}

private inline fun <reified T : RouterProvider> Module.singleController(
    noinline definition: Scope.() -> T
) = single(qualifier = named(T::class.java.canonicalName)) {
    definition().invoke()
}
