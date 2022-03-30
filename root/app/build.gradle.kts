plugins {
    id("dev.monosoul.hexagonal.example.kotlin-application-conventions")
}

dependencies {
    implementation(project(":web-impl"))
    implementation(project(":domain-impl"))
    implementation(project(":persistence-jooq"))
    implementation(project(":thirdparty-http"))
}

application {
    mainClass.set("dev.monosoul.hexagonal.example.app.AppKt")
}
