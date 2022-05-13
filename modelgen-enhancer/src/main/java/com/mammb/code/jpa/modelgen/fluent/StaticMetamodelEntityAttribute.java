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
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Representation of static metamodel attributes.
 *
 * @author Naotsugu Kobayashi
 */
public class StaticMetamodelEntityAttribute {

    /** StaticMetamodelEntity. */
    private final StaticMetamodelEntity entity;
    /** Static metamodel element. */
    private final Element element;

    /** Attribute type. */
    private final AttributeType attributeType;
    /** Type arguments. */
    private final List<String> typeArguments;
    /** Attribute name. */
    private final String name;


    protected StaticMetamodelEntityAttribute(StaticMetamodelEntity entity, Element element) {
        this.entity = entity;
        this.element = element;
        this.attributeType = AttributeType.of(asType(element).asElement().toString());
        this.typeArguments = asType(element).getTypeArguments().stream().map(Object::toString).toList();
        this.name = element.getSimpleName().toString();
    }


    /**
     * Create a new {@link StaticMetamodelEntityAttribute} instance with the given entity.
     * @param entity the static metamodel entity
     * @param element the attribute element
     * @return static metamodel attribute
     */
    public static StaticMetamodelEntityAttribute of(StaticMetamodelEntity entity, Element element) {
        return new StaticMetamodelEntityAttribute(entity, element);
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
        return typeArguments;
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
        var args = asType(element).getTypeArguments();
        var elm = entity.getContext().getTypeUtils().asElement(args.get(args.size() - 1));
        if (Objects.isNull(elm)) return false;
        return elm.getAnnotationMirrors().stream()
            .map(Objects::toString)
            .anyMatch(this::hasEntityAnn);
    }


    private boolean hasEntityAnn(String str) {
        return Stream.of(
                "jakarta.persistence.Entity",
                "jakarta.persistence.Embeddable",
                "jakarta.persistence.MappedSuperclass")
            .anyMatch(str::contains);

    }

    private static DeclaredType asType(Element element) {
        return (DeclaredType) element.asType();
    }

}
