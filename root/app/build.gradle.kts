plugins {
    id("dev.monosoul.hexagonal.example.kotlin-application-conventions")
    id("com.avast.gradle.docker-compose") version "0.15.2"
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

dockerCompose {
    useComposeFiles.set(
        listOf(
            rootProject.file("docker-compose.yml").absolutePath
        )
    )
    captureContainersOutput.set(false)
    stopContainers.set(false)

    isRequiredBy(tasks.run)
}
