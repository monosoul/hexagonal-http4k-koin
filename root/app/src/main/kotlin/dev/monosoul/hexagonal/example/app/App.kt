package dev.monosoul.hexagonal.example.app

import dev.monosoul.hexagonal.domain.impl.domainImplModule
import dev.monosoul.hexagonal.persistence.jooq.PersistenceConfig
import dev.monosoul.hexagonal.persistence.jooq.jooqPersistenceModule
import dev.monosoul.hexagonal.thirdparty.impl.ThirdPartyClientConfig
import dev.monosoul.hexagonal.thirdparty.impl.thirdPartyImplModule
import dev.monosoul.hexagonal.web.impl.WebConfig
import dev.monosoul.hexagonal.web.impl.webImplModule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.lang.Runtime.getRuntime

private val configurations = module {
    single {
        PersistenceConfig(
            host = "localhost",
            port = 6666,
            database = "hexagonal",
            schema = "public",
            username = "hexagonal",
            password = "hexagonal"
        )
    }
    single {
        WebConfig(port = 9999)
    }
    single {
        ThirdPartyClientConfig(protocol = "http", host = "localhost", port = 9966)
    }
}

fun main() {
    startKoin {
        modules(
            configurations + jooqPersistenceModule() + domainImplModule + webImplModule() + thirdPartyImplModule
        )
    }.also {
        getRuntime().addShutdownHook(Thread(it::close, "ShutdownHook"))
    }
}
