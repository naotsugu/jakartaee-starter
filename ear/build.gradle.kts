plugins {
    id("jakartaee.starter.common")
    // use ear plugin
    ear
}

dependencies {

    // The following dependencies will be the ear modules and
    // will be placed in the ear root
    deploy(project(":app", "archives"))
    deploy(project(":web", "archives"))

    // The following dependencies will become ear libs and will
    // be placed in a dir configured via the libDirName property
    earlib("com.h2database:h2:2.1.212")

}

/**
 * Customization of ear plugin
ear {
    // put dependent libraries into APP-INF/lib inside the generated EAR
    libDirName = "APP-INF/lib"
    deploymentDescriptor {  // custom entries for application.xml:
        fileName = "application.xml"  // same as the default value
        version = "6"  // same as the default value
        applicationName = "customear"
        initializeInOrder = true
        displayName = "Custom Ear"  // defaults to project.name
        // defaults to project.description if not set
        description = "My customized EAR for the Gradle documentation"
        libraryDirectory = "APP-INF/lib"  // not needed, above libDirName setting does this
        module("my.jar", "java")  // won't deploy as my.jar isn't deploy dependency
        webModule("my.war", "/")  // won't deploy as my.war isn't deploy dependency
        securityRole("admin")
        securityRole("superadmin")
        withXml { // add a custom node to the XML
            asElement().apply {
                appendChild(ownerDocument.createElement("data-source").apply { textContent = "my/data/source" })
            }
        }
    }
}
*/
