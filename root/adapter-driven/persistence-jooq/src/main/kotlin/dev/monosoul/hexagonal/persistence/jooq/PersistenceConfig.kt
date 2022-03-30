package dev.monosoul.hexagonal.persistence.jooq

data class PersistenceConfig(
    val host: String,
    val port: Short,
    val database: String,
    val schema: String,
    val username: String,
    val password: String,
)
