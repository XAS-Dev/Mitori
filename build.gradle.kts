plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("kapt") version "1.9.24"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.0.1"
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
    jvmToolchain(17)
}