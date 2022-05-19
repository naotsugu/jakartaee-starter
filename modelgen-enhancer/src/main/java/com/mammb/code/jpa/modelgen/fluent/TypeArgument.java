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

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Metamodel attribute type argument.
 * @author Naotsugu Kobayashi
 */
public class TypeArgument {

    private final TypeMirror typeMirror;

    private final Element typeMirrorElement;


    private TypeArgument(TypeMirror typeMirror, Element typeMirrorElement) {
        this.typeMirror = typeMirror;
        this.typeMirrorElement = typeMirrorElement;
    }


    public static TypeArgument of(TypeMirror typeMirror, Element typeMirrorElement) {
        return new TypeArgument(typeMirror, typeMirrorElement);
    }

    /**
     * Get the attribute type argument name.
     * @return the attribute type argument name
     */
    public String getName() {
        return typeMirror.toString();
    }

    /**
     * Get the persistence type.
     * @return the persistence type
     */
    public PersistenceType getPersistenceType() {

        if (Objects.isNull(typeMirrorElement)) {
            return PersistenceType.BASIC;
        }

        var persistenceTypes = Arrays.stream(PersistenceType.values())
            .collect(Collectors.toMap(PersistenceType::getFqcn, UnaryOperator.identity()));

        return typeMirrorElement.getAnnotationMirrors().stream()
            .map(am -> am.getAnnotationType().toString())
            .map(persistenceTypes::get)
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(PersistenceType.BASIC);
    }

    /**
     * Gets whether PersistenceType is a structure or not.
     * @return if PersistenceType is a structure, then {@code true}
     */
    public boolean isStruct() {
        return getPersistenceType() == PersistenceType.ENTITY
            || getPersistenceType() == PersistenceType.EMBEDDABLE
            || getPersistenceType() == PersistenceType.MAPPED_SUPERCLASS;
    }


    /**
     * Gets whether PersistenceType is a basic or not.
     * @return if PersistenceType is a basic, then {@code true}
     */
    public boolean isBasic() {
        return getPersistenceType() == PersistenceType.BASIC;
    }

}
