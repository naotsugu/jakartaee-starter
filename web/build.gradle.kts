plugins {
    id("jakartaee.starter.common")
    war
}

val earEnabled = try { project(":ear"); true; } catch (e: Exception) { false }

dependencies {
    compileOnly("jakarta.platform:jakarta.jakartaee-api")
    compileOnly("com.h2database:h2:2.1.214")
    if (earEnabled) {
        providedCompile(project(":app"))
    } else {
        implementation(project(":app", "archives"))
    }
    compileOnly(project(":domain"))
    compileOnly(project(":lib"))
}


