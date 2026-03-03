plugins {
    // Provides support for Java projects
    java
    // Allows the project to be run as an application via './gradlew run'
    application
    id("io.freefair.lombok") version "9.2.0"
}

group = "com.atlas"
version = "1.0-SNAPSHOT"

repositories {
    // Use Maven Central to download JUnit 5 and other dependencies
    mavenCentral()
}

dependencies {
    // JUnit 5 Platform and Jupiter Engine for testing (MLO10)
    testImplementation(platform("org.junit:junit-bom:6.0.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // SLF4J for logging (MLO8 implementation detail)
    implementation("org.slf4j:slf4j-simple:2.0.17")
}

java {
    // MLO7: Enforce Java 25 toolchain for modern language features
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

application {
    // Defines the entry point for './gradlew run'
    mainClass.set("com.atlas.Main")
}

tasks.test {
    // Ensures Gradle uses the JUnit Platform to run tests
    useJUnitPlatform()

    // Provides helpful console output during testing
    testLogging {
        events("passed", "skipped", "failed")
        showExceptions = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}
