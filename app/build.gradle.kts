plugins {
    id("jakartaee.starter.common")
    `java-library`
}

dependencies {
    compileOnly("jakarta.platform:jakarta.jakartaee-api")
}

dependencies {
    api(project(":lib"))
    api(project(":domain"))
}


tasks.jar {
    // Create uberjar. Include dependencies project.
    from(sourceSets.main.get().output)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}
