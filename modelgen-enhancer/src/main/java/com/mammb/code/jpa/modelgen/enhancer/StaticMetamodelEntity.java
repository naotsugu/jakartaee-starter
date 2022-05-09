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

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import java.util.List;

/**
 * Representation of static metamodel.
 *
 * @author Naotsugu Kobayashi
 */
public class StaticMetamodelEntity {

    private final Context context;
    private final TypeElement element;

    private final List<StaticMetamodelEntityAttribute> attributes;


    protected StaticMetamodelEntity(Context context, TypeElement element) {
        this.context = context;
        this.element = element;
        this.attributes = ElementFilter.fieldsIn(element.getEnclosedElements()).stream()
            .filter(e -> e.asType().toString().startsWith(AttributeType.PACKAGE_NAME))
            .map(e -> StaticMetamodelEntityAttribute.of(this, e))
            .toList();
    }

    public static StaticMetamodelEntity of(Context context, TypeElement element) {
        return new StaticMetamodelEntity(context, element);
    }

    public String getTargetEntityName() {
        return getSimpleName().substring(0, getSimpleName().length() - 1);
    }

    public String getSimpleName() {
        return element.getSimpleName().toString();
    }

    public String getQualifiedName() {
        return element.getQualifiedName().toString();
    }

    public String getPackageName() {
        PackageElement packageOf = context.getElementUtils().getPackageOf(element);
        return context.getElementUtils().getName(packageOf.getQualifiedName()).toString();
    }

    public String getSuperClass() {
        TypeMirror superClass = element.getSuperclass();
        return (Object.class.getCanonicalName().equals(superClass.toString())) ? null : superClass.toString();
    }

    public TypeElement getElement() {
        return element;
    }


    public List<StaticMetamodelEntityAttribute> getAttributes() {
        return attributes;
    }

    Context getContext() {
        return context;
    }

}
