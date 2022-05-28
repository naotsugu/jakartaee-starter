package com.mammb.jakartaee.starter.app.example.entitymeta;

import jakarta.persistence.metamodel.Attribute;

public class Column {

    public final String name;
    public final String javaTypeName;
    public final String persistentAttributeTypeName;
    public final String javaMemberName;
    public final String declaringType;
    public final boolean isAssociation;
    public final boolean isCollection;

    public <X> Column(Attribute<? super X, ?> attribute) {
        this.name = attribute.getName();
        this.javaTypeName = attribute.getJavaType().getName();
        this.persistentAttributeTypeName = attribute.getPersistentAttributeType().name();
        this.javaMemberName = attribute.getJavaMember().getName();
        this.declaringType = attribute.getDeclaringType().toString();
        this.isAssociation = attribute.isAssociation();
        this.isCollection = attribute.isCollection();
    }
}
