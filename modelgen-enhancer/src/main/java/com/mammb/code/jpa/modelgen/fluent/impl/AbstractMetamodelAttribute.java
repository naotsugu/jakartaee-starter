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
package com.mammb.code.jpa.modelgen.fluent.impl;

import com.mammb.code.jpa.modelgen.fluent.MetamodelAttribute;
import com.mammb.code.jpa.modelgen.fluent.TypeArgument;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Types;
import java.util.List;

/**
 * Static Metamodel Attribute.
 * @author Naotsugu Kobayashi
 */
public class AbstractMetamodelAttribute implements MetamodelAttribute {

    /** Static metamodel element. */
    private final Element element;

    /** Static metamodel type arguments. */
    private final List<TypeArgument> typeArguments;


    public AbstractMetamodelAttribute(Element element, Types types) {
        this.element = element;
        this.typeArguments = asType(element).getTypeArguments().stream()
            .map(t -> TypeArgumentImpl.of(t, types)).toList();
        if (typeArguments.size() < 2) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String getName() {
        return element.getSimpleName().toString();
    }

    @Override
    public TypeArgument getEnclosing() {
        return typeArguments.get(0);
    }

    @Override
    public TypeArgument getKeyType() {
        return typeArguments.size() > 2 ? typeArguments.get(1) : null;
    }

    @Override
    public TypeArgument getValueType() {
        return typeArguments.get(typeArguments.size() - 1);
    }

    private static DeclaredType asType(Element element) {
        if (element instanceof DeclaredType d) {
            return d;
        } else {
            throw new IllegalArgumentException();
        }
    }

}
