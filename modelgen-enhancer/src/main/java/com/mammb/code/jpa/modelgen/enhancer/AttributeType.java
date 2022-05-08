package com.mammb.code.jpa.modelgen.enhancer;

import java.util.Arrays;

public enum AttributeType {

    SINGULAR_ATTRIBUTE("SingularAttribute"),
    LIST_ATTRIBUTE("ListAttribute"),
    SET_ATTRIBUTE("SetAttribute"),
    COLLECTION_ATTRIBUTE("CollectionAttribute"),
    MAP_ATTRIBUTE("MapAttribute"),
    ;

    public boolean isSingular() {
        return this == SINGULAR_ATTRIBUTE;
    }
    public boolean isList() {
        return this == LIST_ATTRIBUTE;
    }
    public boolean isSet() {
        return this == SET_ATTRIBUTE;
    }
    public boolean isCollection() {
        return this == COLLECTION_ATTRIBUTE;
    }
    public boolean isMap() {
        return this == MAP_ATTRIBUTE;
    }


    public static final String PACKAGE_NAME = "jakarta.persistence.metamodel.";

    private final String fqcn;

    AttributeType(String name) {
        this.fqcn = PACKAGE_NAME + name;
    }

    public static AttributeType of(String fqcn) {
        return Arrays.stream(values())
            .filter(e -> e.fqcn.equals(fqcn))
            .findFirst().orElseThrow();
    }

    public String getFqcn() {
        return fqcn;
    }

    public String getSimpleName() {
        return fqcn.replace(PACKAGE_NAME, "");
    }

}
