/*
 * Copyright 2022-2023 the original author or authors.
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

/**
 * Metamodel attribute type argument.
 * @author Naotsugu Kobayashi
 */
public interface TypeArgument {

    /**
     * Get the attribute type argument name.
     * @return the attribute type argument name
     */
    String getName();

    /**
     * Get the persistence type.
     * @return the persistence type
     */
    PersistenceType getPersistenceType();


    /**
     * Gets whether PersistenceType is a structure or not.
     * @return if PersistenceType is a structure, then {@code true}
     */
    default boolean isStruct() {
        return getPersistenceType() == PersistenceType.ENTITY
            || getPersistenceType() == PersistenceType.EMBEDDABLE
            || getPersistenceType() == PersistenceType.MAPPED_SUPERCLASS;
    }


    /**
     * Gets whether PersistenceType is a basic or not.
     * @return if PersistenceType is a basic, then {@code true}
     */
    default boolean isBasic() {
        return getPersistenceType() == PersistenceType.BASIC;
    }


    /** Persistence type. */
    enum PersistenceType {
        /** Entity. */
        ENTITY("jakarta.persistence.Entity"),
        /** Embeddable class. */
        EMBEDDABLE("jakarta.persistence.Embeddable"),
        /** Mapped superclass. */
        MAPPED_SUPERCLASS("jakarta.persistence.MappedSuperclass"),
        /** Basic type. */
        BASIC(""),
        ;

        private final String fqcn;

        PersistenceType(String fqcn) {
            this.fqcn = fqcn;
        }

        public String getFqcn() {
            return fqcn;
        }
    }

}
