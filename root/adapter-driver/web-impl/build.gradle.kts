plugins {
    id("dev.monosoul.hexagonal.example.kotlin-library-conventions")
}

dependencies {
    implementation(project(":domain-api"))

    implementation("io.insert-koin:koin-core-jvm")
}
