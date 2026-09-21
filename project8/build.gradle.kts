plugins {
    kotlin("jvm")
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
}

application {
    // Pick the entry point with -Pentry=..., default to the summary version
    mainClass.set(
        providers.gradleProperty("entry").getOrElse("org.example.3. MultipleProducersWithWorkersSummary")
    )
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(24)
}