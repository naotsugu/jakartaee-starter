plugins {
    id("jakartaee.starter.common")
    war
}

val earEnabled = try { project(":ear"); true; } catch (e: Exception) { false }

dependencies {
    compileOnly("jakarta.platform:jakarta.jakartaee-api")
    if (earEnabled) {
        providedCompile(project(":app"))
    } else {
        implementation(project(":app", "archives"))
    }
    compileOnly(project(":domain"))
    compileOnly(project(":lib"))
}


