plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("kapt") version "2.1.10"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}