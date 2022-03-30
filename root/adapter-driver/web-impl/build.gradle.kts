plugins {
    id("dev.monosoul.hexagonal.example.kotlin-library-conventions")
}

dependencies {
    implementation(project(":domain-api"))

    implementation("io.insert-koin:koin-core-jvm")
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-server-undertow")
    implementation("org.http4k:http4k-format-kotlinx-serialization")
}
