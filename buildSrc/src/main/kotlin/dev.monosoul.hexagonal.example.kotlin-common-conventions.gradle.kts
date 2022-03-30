import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STARTED
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm")
    `version-catalog`
}

repositories {
    mavenCentral()
}

dependencies {
    constraints {
        implementation("io.insert-koin:koin-core-jvm:3.1.5")
    }

    implementation(enforcedPlatform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation(enforcedPlatform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation(enforcedPlatform("io.strikt:strikt-bom:0.34.1"))
    testImplementation("io.strikt:strikt-jvm")

    testImplementation("io.mockk:mockk:1.12.3")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            events(FAILED, PASSED, STARTED)
            showExceptions = true
            showCauses = true
            showStackTraces = true
            exceptionFormat = FULL
        }
    }
}
