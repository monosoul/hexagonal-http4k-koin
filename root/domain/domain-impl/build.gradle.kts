plugins {
    id("dev.monosoul.hexagonal.example.kotlin-library-conventions")
}

dependencies {
    api(project(":domain-api"))
    implementation(project(":domain-persistence-spi"))
    implementation(project(":domain-thirdparty-spi"))
}
