package dev.monosoul.hexagonal.example.app

import dev.monosoul.hexagonal.persistence.jooq.PersistenceConfig
import dev.monosoul.hexagonal.persistence.jooq.jooqPersistenceModule
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
}

fun main() {
    startKoin {
        modules(
            configurations + jooqPersistenceModule
        )
    }.also {
        getRuntime().addShutdownHook(Thread(it::close, "ShutdownHook"))
    }
}
