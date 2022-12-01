plugins {
    java
}

repositories {
    mavenCentral()
}

tasks.withType<JavaCompile> {
    options.encoding = Charsets.UTF_8.name()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    constraints {
        // Define dependency versions as constraints
        implementation("jakarta.platform:jakarta.jakartaee-api:10.0.0")
    }
    // Use JUnit Jupiter for testing.
    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

val earEnabled = try { project(":ear"); true; } catch (e: Exception) { false }
