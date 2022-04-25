plugins {
    id("jakartaee.starter.common")
}

dependencies {
    annotationProcessor("org.hibernate:hibernate-jpamodelgen:6.0.0.Final")
    compileOnly("jakarta.platform:jakarta.jakartaee-api")
}
