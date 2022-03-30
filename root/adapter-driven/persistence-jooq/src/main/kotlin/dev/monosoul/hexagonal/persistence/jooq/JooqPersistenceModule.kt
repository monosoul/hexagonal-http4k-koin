package dev.monosoul.hexagonal.persistence.jooq

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.monosoul.hexagonal.domain.persistence.spi.MessageRepository
import dev.monosoul.hexagonal.persistence.jooq.generated.Public.PUBLIC
import dev.monosoul.hexagonal.persistence.jooq.impl.MessageRepositoryImpl
import org.flywaydb.core.Flyway
import org.jooq.DSLContext
import org.jooq.SQLDialect.POSTGRES
import org.jooq.conf.MappedSchema
import org.jooq.conf.RenderMapping
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.dsl.onClose
import javax.sql.DataSource

val jooqPersistenceModule = module {
    single {
        val config = get<PersistenceConfig>()

        HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://${config.host}:${config.port}/${config.database}"
            username = config.username
            password = config.password
        }
    }

    single<DataSource> {
        HikariDataSource(get())
    }.onClose { (it as? HikariDataSource)?.close() }

    single<Flyway> {
        val config = get<PersistenceConfig>()

        Flyway.configure()
            .dataSource(get())
            .schemas(config.schema)
            .load()
    }

    single<Runnable>(qualifier = named("flywayInitializer"), createdAtStart = true) {
        Runnable { get<Flyway>().migrate() }.also { it.run() }
    }

    single<DSLContext> {
        DSL.using(
            get<DataSource>(),
            POSTGRES,
            get<PersistenceConfig>().toJooqSettings()
        )
    }

    single<MessageRepository> {
        MessageRepositoryImpl(get())
    }
}

private fun PersistenceConfig.toJooqSettings(): Settings = Settings()
    .withRenderMapping(
        RenderMapping()
            .withSchemata(
                MappedSchema()
                    .withInput(PUBLIC.name)
                    .withOutput(schema)
            )
    )
