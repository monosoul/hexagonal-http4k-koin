import dev.monosoul.jooq.RecommendedVersions.FLYWAY_VERSION
import dev.monosoul.jooq.RecommendedVersions.JOOQ_VERSION
import org.jooq.meta.jaxb.ForcedType

plugins {
    id("dev.monosoul.hexagonal.example.kotlin-library-conventions")
    id("dev.monosoul.jooq-docker") version "4.0.1"
}

dependencies {
    api(project(":domain-persistence-spi"))

    implementation("io.insert-koin:koin-core-jvm")
    implementation("org.jooq:jooq:$JOOQ_VERSION")

    val postgresJdbcVersion = "42.3.8"
    jooqCodegen("org.postgresql:postgresql:$postgresJdbcVersion")
    implementation("org.postgresql:postgresql:$postgresJdbcVersion")
    implementation("org.flywaydb:flyway-core:$FLYWAY_VERSION")
    implementation("com.zaxxer:HikariCP:5.0.1")
}

tasks {
    generateJooqClasses {
        basePackageName.set("dev.monosoul.hexagonal.persistence.jooq.generated")
        withContainer {
            image {
                name = "postgres:13.4-alpine"
            }
        }
        usingJavaConfig {
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
