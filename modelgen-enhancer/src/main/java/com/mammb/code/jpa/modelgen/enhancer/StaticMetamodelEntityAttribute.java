package com.mammb.code.jpa.modelgen.enhancer;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class StaticMetamodelEntityAttribute {

    private final StaticMetamodelEntity entity;
    private final Element element;

    private final AttributeType attributeType;
    private final List<String> typeArguments;
    private final String name;


    public StaticMetamodelEntityAttribute(StaticMetamodelEntity entity, Element element) {
        this.entity = entity;
        this.element = element;
        this.attributeType = AttributeType.of(asType(element).asElement().toString());
        this.typeArguments = asType(element).getTypeArguments().stream().map(Object::toString).toList();
        this.name = element.getSimpleName().toString();
    }


    public static StaticMetamodelEntityAttribute of(StaticMetamodelEntity entity, Element element) {
        return new StaticMetamodelEntityAttribute(entity, element);
    }


    public AttributeType getAttributeType() {
        // e.g jakarta.persistence.metamodel.SingularAttribute
        return attributeType;
    }


    public List<String> getTypeArguments() {
        // e.g foo.bar.Customer, java.lang.String
        return typeArguments;
    }

    public String getName() {
        // e.g userName
        return name;
    }

    public boolean isEntityTypeTo() {
        var args = asType(element).getTypeArguments();
        return entity.getContext().getTypeUtils().asElement(args.get(args.size() - 1))
            .getAnnotationMirrors().stream()
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
