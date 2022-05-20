plugins {
    id("jakartaee.starter.common")
}

dependencies {
    testImplementation("jakarta.persistence:jakarta.persistence-api:3.0.0")
    testAnnotationProcessor("org.hibernate.orm:hibernate-jpamodelgen:6.0.0.Final")
    testAnnotationProcessor(project(":modelgen-enhancer", "archives"))
}
