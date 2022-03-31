package dev.monosoul.hexagonal.example.app

import dev.monosoul.hexagonal.domain.impl.domainImplModule
import dev.monosoul.hexagonal.persistence.jooq.PersistenceConfig
import dev.monosoul.hexagonal.persistence.jooq.jooqPersistenceModule
import dev.monosoul.hexagonal.thirdparty.impl.ThirdPartyClientConfig
import dev.monosoul.hexagonal.thirdparty.impl.thirdPartyImplModule
import dev.monosoul.hexagonal.web.impl.WebConfig
import dev.monosoul.hexagonal.web.impl.webImplModule
import org.koin.core.context.startKoin
import java.lang.Runtime.getRuntime

val persistenceConfig = PersistenceConfig(
    host = "localhost",
    port = 6666,
    database = "hexagonal",
    schema = "public",
    username = "hexagonal",
    password = "hexagonal"
)

val webConfig = WebConfig(port = 9999)

val thirdPartyClientConfig = ThirdPartyClientConfig(protocol = "http", host = "localhost", port = 9966)

fun main() {
    startKoin {
        modules(
            domainImplModule
                    + jooqPersistenceModule(persistenceConfig)
                    + webImplModule(webConfig)
                    + thirdPartyImplModule(thirdPartyClientConfig)
        )
    }.also {
        getRuntime().addShutdownHook(Thread(it::close, "ShutdownHook"))
    }
}
