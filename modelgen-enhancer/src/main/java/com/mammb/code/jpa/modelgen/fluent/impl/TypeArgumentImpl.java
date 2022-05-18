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

import com.mammb.code.jpa.modelgen.fluent.TypeArgument;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Metamodel attribute type argument.
 * @author Naotsugu Kobayashi
 */
public class TypeArgumentImpl implements TypeArgument {

    private final TypeMirror typeMirror;

    private final Types types;


    private TypeArgumentImpl(TypeMirror typeMirror, Types types) {
        this.typeMirror = typeMirror;
        this.types = types;
    }


    public static TypeArgument of(TypeMirror typeMirror, Types types) {
        return new TypeArgumentImpl(typeMirror, types);
    }


    @Override
    public String getName() {
        return typeMirror.toString();
    }


    @Override
    public PersistenceType getPersistenceType() {

        var elm = types.asElement(typeMirror);

        if (Objects.isNull(elm)) {
            return PersistenceType.BASIC;
        }

        var persistenceTypes = Arrays.stream(PersistenceType.values())
            .collect(Collectors.toMap(PersistenceType::getFqcn, UnaryOperator.identity()));

        return elm.getAnnotationMirrors().stream()
            .map(Objects::toString)
            .map(persistenceTypes::get)
            .filter(Objects::nonNull)
            .findFirst().orElse(PersistenceType.BASIC);
    }

}
