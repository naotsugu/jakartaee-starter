plugins {
    id("jakartaee.starter.common")
}

dependencies {
    annotationProcessor("org.hibernate.orm:hibernate-jpamodelgen:6.1.5.Final")
    compileOnly("jakarta.platform:jakarta.jakartaee-api")
    implementation(project(":lib"))
}

