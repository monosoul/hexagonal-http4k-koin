import org.jooq.meta.jaxb.ForcedType

plugins {
    id("dev.monosoul.hexagonal.example.kotlin-library-conventions")
    id("com.revolut.jooq-docker") version "0.3.7"
}

buildscript {
    val jooqVersion by project.extra { "3.16.5" }
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jooq:jooq-codegen:$jooqVersion")
    }
}

dependencies {
    api(project(":domain-persistence-spi"))

    implementation("io.insert-koin:koin-core-jvm")
    val jooqVersion: String by project.extra
    implementation("org.jooq:jooq:$jooqVersion")

    val postgresJdbcVersion = "42.3.3"
    jdbc("org.postgresql:postgresql:$postgresJdbcVersion")
    implementation("org.postgresql:postgresql:$postgresJdbcVersion")
    implementation("org.flywaydb:flyway-core:8.5.5")
    implementation("com.zaxxer:HikariCP:5.0.1")
}

jooq {
    image {
        tag = "13.4-alpine"
    }
}

tasks {
    generateJooqClasses {
        basePackageName = "dev.monosoul.hexagonal.persistence.jooq.generated"
        excludeFlywayTable = true
        customizeGenerator {
            database.apply {
                fun forcedType(
                    type: String,
                    includeExpression: String,
                    includeTypes: String = ".*"
                ) = ForcedType()
                    .withUserType(type)
                    .withConverter("dev.monosoul.hexagonal.persistence.jooq.JooqConverters.get(\"$type\")")
                    .withIncludeExpression(includeExpression)
                    .withIncludeTypes(includeTypes)

                withForcedTypes(
                    forcedType(
                        type = "dev.monosoul.hexagonal.domain.model.MessageId",
                        includeExpression = ".*\\.messages\\.id",
                        includeTypes = "uuid"
                    ),
                    forcedType(
                        type = "dev.monosoul.hexagonal.domain.model.MessageBody",
                        includeExpression = ".*\\.messages\\.body",
                        includeTypes = "jsonb"
                    ),
                )
            }
        }
    }
}
