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

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Representation of static metamodel attributes.
 *
 * @author Naotsugu Kobayashi
 */
public class StaticMetamodelAttribute {

    /** Static metamodel element. */
    private final Element element;

    /** Attribute type. */
    private final AttributeType attributeType;

    /** Type arguments. */
    private final List<TypeArgument> typeArguments;

    /** Attribute name. */
    private final String name;


    /**
     * Constructor.
     */
    protected StaticMetamodelAttribute(Element element, Types types) {

        this.element = element;
        this.name = element.getSimpleName().toString();

        final var declaredType = asType(element);

        this.attributeType = AttributeType.of(declaredType.asElement());

        this.typeArguments = declaredType.getTypeArguments().stream()
            .map(t -> TypeArgument.of(t, types.asElement(t)))
            .toList();

        if (typeArguments.size() < 2) {
            throw new IllegalArgumentException();
        }

    }


    /**
     * Create a new {@link StaticMetamodelAttribute} instance with the given entity.
     * @param element the attribute element
     * @param types the type utils
     * @return static metamodel attribute
     */
    public static StaticMetamodelAttribute of(Element element, Types types) {
        return new StaticMetamodelAttribute(element, types);
    }


    /**
     * Get the attribute type.
     * e.g. jakarta.persistence.metamodel.SingularAttribute
     * @return the attribute type
     */
    public AttributeType getAttributeType() {
        return attributeType;
    }


    /**
     * Get the type arguments of attribute.
     * e.g. foo.bar.Customer, java.lang.String
     * @return the type arguments
     */
    public List<String> getTypeArguments() {
        return typeArguments.stream().map(TypeArgument::getName).toList();
    }


    /**
     * Get the attribute name.
     * e.g. userName
     * @return the attribute name
     */
    public String getName() {
        return name;
    }


    /**
     * Gets whether this attribute refers to an Entity or not.
     * @return If this attribute refers to an Entity, return {@code true}
     */
    public boolean isEntityTypeTo() {
        return typeArguments.get(typeArguments.size() - 1).isStruct();
    }


    /**
     * Cast as declared type.
     * @param element the element
     * @return the declared type
     */
    private static DeclaredType asType(Element element) {
        if (element.asType() instanceof DeclaredType declaredType) {
            return declaredType;
        }
        throw new IllegalArgumentException(element.toString());
    }

}
