plugins {
    id("dev.monosoul.hexagonal.example.kotlin-library-conventions")
}

dependencies {
    api(project(":domain-persistence-spi"))

    implementation("io.insert-koin:koin-core-jvm")
}
