package dev.monosoul.hexagonal.domain.impl

import dev.monosoul.hexagonal.domain.api.MessageService
import org.koin.dsl.module

val domainImplModule = module {
    single<MessageService> {
        MessageServiceImpl(messageRepository = get(), messageClient = get())
    }
}
