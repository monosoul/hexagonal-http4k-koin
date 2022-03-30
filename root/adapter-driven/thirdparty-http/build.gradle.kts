plugins {
    id("dev.monosoul.hexagonal.example.kotlin-library-conventions")
}

dependencies {
    api(project(":domain-thirdparty-spi"))

    implementation("io.insert-koin:koin-core-jvm")
    implementation("org.http4k:http4k-client-apache")
    implementation("org.http4k:http4k-format-kotlinx-serialization")
}
