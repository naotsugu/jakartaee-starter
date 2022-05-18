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

import com.mammb.code.jpa.modelgen.fluent.impl.CollectionMetamodelAttribute;
import com.mammb.code.jpa.modelgen.fluent.impl.ListMetamodelAttribute;
import com.mammb.code.jpa.modelgen.fluent.impl.MapMetamodelAttribute;
import com.mammb.code.jpa.modelgen.fluent.impl.SetMetamodelAttribute;
import com.mammb.code.jpa.modelgen.fluent.impl.SingularMetamodelAttribute;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Types;

/**
 * Static Metamodel Attribute.
 *
 * e.g.
 * <pre>{@code
 *   public static volatile SingularAttribute<Entity, String> userName;
 * }</pre>
 */
public interface MetamodelAttribute {

    /**
     * Get the attribute name.
     * e.g. userName
     * @return The attribute name
     */
    String getName();

    /**
     * Get the type containing the represented attribute.
     * @return The type containing the represented attribute
     */
    TypeArgument getEnclosing();

    /**
     * Get the type of the key of the represented Map.
     * @return The type of the key of the represented Map
     */
    TypeArgument getKeyType();

    /**
     * Get the type of the represented attribute.
     * @return The type of the represented attribute
     */
    TypeArgument getValueType();


    static MetamodelAttribute of(Element element, Types types) {
        return switch (AttributeType.of(asType(element).asElement().toString())) {
            case SINGULAR_ATTRIBUTE   -> new SingularMetamodelAttribute(element, types);
            case LIST_ATTRIBUTE       -> new ListMetamodelAttribute(element, types);
            case SET_ATTRIBUTE        -> new SetMetamodelAttribute(element, types);
            case COLLECTION_ATTRIBUTE -> new CollectionMetamodelAttribute(element, types);
            case MAP_ATTRIBUTE        -> new MapMetamodelAttribute(element, types);
        };
    }

    private static DeclaredType asType(Element element) {
        if (element instanceof DeclaredType d) {
            return d;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
