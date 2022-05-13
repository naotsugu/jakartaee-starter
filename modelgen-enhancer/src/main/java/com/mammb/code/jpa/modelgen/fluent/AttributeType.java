/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mammb.code.jpa.modelgen.fluent;

import java.util.Arrays;

/**
 * JPA static metamodel attribute type.
 *
 * @author Naotsugu Kobayashi
 */
public enum AttributeType {

    /** SingularAttribute. */
    SINGULAR_ATTRIBUTE("SingularAttribute"),
    /** ListAttribute. */
    LIST_ATTRIBUTE("ListAttribute"),
    /** SetAttribute. */
    SET_ATTRIBUTE("SetAttribute"),
    /** CollectionAttribute. */
    COLLECTION_ATTRIBUTE("CollectionAttribute"),
    /** MapAttribute. */
    MAP_ATTRIBUTE("MapAttribute"),
    ;


    /**
     * Determines if it is SingularAttribute or not.
     * @return if it is SingularAttribute, return {@code true}
     */
    public boolean isSingular() {
        return this == SINGULAR_ATTRIBUTE;
    }


    /**
     * Determines if it is ListAttribute or not.
     * @return if it is ListAttribute, return {@code true}
     */
    public boolean isList() {
        return this == LIST_ATTRIBUTE;
    }


    /**
     * Determines if it is SetAttribute or not.
     * @return if it is SetAttribute, return {@code true}
     */
    public boolean isSet() {
        return this == SET_ATTRIBUTE;
    }


    /**
     * Determines if it is CollectionAttribute or not.
     * @return if it is CollectionAttribute, return {@code true}
     */
    public boolean isCollection() {
        return this == COLLECTION_ATTRIBUTE;
    }


    /**
     * Determines if it is MapAttribute or not.
     * @return if it is MapAttribute, return {@code true}
     */
    public boolean isMap() {
        return this == MAP_ATTRIBUTE;
    }

    /** Attribute package name. */
    public static final String PACKAGE_NAME = "jakarta.persistence.metamodel.";

    /** Attribute FQCN. */
    private final String fqcn;


    /**
     * Create AttributeType by given name.
     * @param name the Attribute name
     */
    AttributeType(String name) {
        this.fqcn = PACKAGE_NAME + name;
    }


    /**
     * Select attribute type by FQCN.
     * @param fqcn FQCN
     * @return the Attribute
     */
    public static AttributeType of(String fqcn) {
        return Arrays.stream(values())
            .filter(e -> e.fqcn.equals(fqcn))
            .findFirst()
            .orElseThrow();
    }


    /**
     * Get attribute FQCN.
     * @return the FQCN
     */
    public String getFqcn() {
        return fqcn;
    }


    /**
     * Get attribute simple name.
     * @return the attribute simple name
     */
    public String getSimpleName() {
        return fqcn.replace(PACKAGE_NAME, "");
    }

}
