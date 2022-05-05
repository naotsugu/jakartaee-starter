plugins {
    id("jakartaee.starter.common")
}

dependencies {
    implementation("org.hibernate.orm:hibernate-jpamodelgen:6.0.0.Final")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    // Create uberjar. Include dependencies project.
    from(sourceSets.main.get().output)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it).matching{exclude{ it.path.contains("META-INF") } } }
    })
}
