package dev.monosoul.hexagonal.thirdparty.impl

import dev.monosoul.hexagonal.domain.thirdparty.spi.MessageClient
import org.http4k.client.ApacheClient
import org.koin.dsl.module

val thirdPartyImplModule = module {
    single<MessageClient> {
        MessageClientImpl(client = ApacheClient(), clientConfig = get())
    }
}
