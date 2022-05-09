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
package com.mammb.code.jpa.modelgen.enhancer;

import java.util.Arrays;

/**
 * JPA static metamodel attribute type.
 *
 * @author Naotsugu Kobayashi
 */
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
            .findFirst()
            .orElseThrow();
    }

    public String getFqcn() {
        return fqcn;
    }

    public String getSimpleName() {
        return fqcn.replace(PACKAGE_NAME, "");
    }

}
