plugins {
    id("jakartaee.starter.common")
}

dependencies {
    //annotationProcessor("org.hibernate.orm:hibernate-jpamodelgen:6.0.0.Final")
    annotationProcessor(project(":modelgen", "archives"))
    compileOnly("jakarta.platform:jakarta.jakartaee-api")
    implementation(project(":lib"))
}
