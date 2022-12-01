plugins {
    // Apply the common convention plugin for shared build configuration between library and application projects.
    id("jakartaee.starter.common")
    // Apply the application plugin to add support for building a application in Java.
    application
}

dependencies {
    implementation("fish.payara.extras:payara-embedded-all:6.2022.1")
    //implementation("org.glassfish.main.extras:glassfish-embedded-all:6.2.5")
    //implementation("com.h2database:h2:2.1.212")
}


// copy archive to resources dir
tasks.register<Copy>("copyArchive") {

    delete(layout.projectDirectory.dir("src/main/resources/ear.ear"))
    delete(layout.projectDirectory.dir("src/main/resources/web.war"))

    val earEnabled = try { project(":ear"); true; } catch (e: Exception) { false }
    if (earEnabled) {
        from(project(":ear").tasks.named<Ear>("ear").get().archiveFile)
    } else {
        from(project(":web").tasks.named<War>("war").get().archiveFile)
    }
    into(layout.projectDirectory.dir("src/main/resources"))
}
tasks.processResources {
    dependsOn("copyArchive")
}


application {
    // Application entry point
    mainClass.set("com.mammb.jakartaee.starter.dev.server.Main")


    applicationDefaultJvmArgs += listOf(
        "--add-opens", "java.base/java.lang=ALL-UNNAMED",
        "--add-opens", "java.base/java.net=ALL-UNNAMED",
        "--add-opens", "java.base/java.io=ALL-UNNAMED",
        "--add-opens", "java.base/sun.net.www.protocol.jar=ALL-UNNAMED",
        "-Duser.language=en",
        "-Denv=dev",  // development mode
        "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005")
}
