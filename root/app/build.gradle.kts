plugins {
    id("dev.monosoul.hexagonal.example.kotlin-application-conventions")
}

dependencies {
    implementation(project(":web-impl"))
    implementation(project(":domain-impl"))
    implementation(project(":persistence-jooq"))
    implementation(project(":thirdparty-http"))

    implementation("io.insert-koin:koin-core-jvm")
    implementation("ch.qos.logback:logback-classic:1.2.11")
}

application {
    mainClass.set("dev.monosoul.hexagonal.example.app.AppKt")
}
