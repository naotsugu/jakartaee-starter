package com.mammb.jakartaee.starter.lib.modelgen;

import org.hibernate.jpamodelgen.Context;
import org.hibernate.jpamodelgen.model.MetaAttribute;
import org.hibernate.jpamodelgen.model.MetaEntity;
import org.hibernate.jpamodelgen.util.Constants;
import org.hibernate.jpamodelgen.util.TypeUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Objects;
import java.util.regex.Pattern;

public class ClassWriteEnhancer {

    public static String generateEnhanceImports() {
        return """
            import jakarta.persistence.criteria.Expression;
            import jakarta.persistence.criteria.Root;
            import jakarta.persistence.criteria.Join;
            import jakarta.persistence.criteria.Path;
            import jakarta.persistence.criteria.ListJoin;
            import jakarta.persistence.criteria.SetJoin;
            import jakarta.persistence.criteria.MapJoin;
            import java.util.List;
            import java.util.Map;
            import java.util.Set;
            import java.util.function.Supplier;
            """;
    }

    public static String generateEnhanceRootClass(MetaEntity entity, Context context) {
        StringBuilder sb = new StringBuilder();

        String superClassName = findMappedSuperClass(entity, context);
        if (Objects.isNull(superClassName)) {
            var sign = """
                    public static class Root_ implements Supplier<Root<? extends %s>> {
                        private final Root<? extends %s> root;
                        public Root_(Root<? extends %s> root) {
                            this.root = root;
                        }
                        @Override
                        public Root<? extends %s> get() {
                            return root;
                        }
                """.formatted(entity.getSimpleName(), entity.getSimpleName(), entity.getSimpleName(), entity.getSimpleName());
            sb.append(sign).append('\n');
        } else {
            var sign = """
                    public static class Root_ extends %s_.Root_ {
                        public Root_(Root<? extends %s> root) {
                            super(root);
                        }
                """.formatted(superClassName, entity.getSimpleName());
            sb.append(sign).append('\n');
        }
        entity.getMembers().forEach(m -> sb.append(generateEnhanceRootPluralMethod(entity, m, context)));
        sb.append("    }").append('\n');
        return sb.toString();
    }


    private static String generateEnhanceRootPluralMethod(MetaEntity entity, MetaAttribute metaMember, Context context) {
        if (metaMember.getMetaType().endsWith(".ListAttribute")) {
            return """
                    public %s_.Join_ join%s() {
                        return new %s_.Join_() {
                            @Override public ListJoin<%s> get() {
                                return ((Root<%s>) Root_.this.get()).join(%s_.%s);
                            }
                        };
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                extractCollection(metaMember.getAttributeDeclarationString()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName())
                + """
                    public Expression<List<%s>> get%s() {
                        return ((Root<%s>) Root_.this.get()).get(%s_.%s);
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());

        } else if (metaMember.getMetaType().endsWith(".SetAttribute")) {
            return """
                    public %s_.Join_ join%s() {
                        return new %s_.Join_() {
                            @Override public SetJoin<%s> get() {
                                return ((Root<%s>) Root_.this.get()).join(%s_.%s);
                            }
                        };
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                extractCollection(metaMember.getAttributeDeclarationString()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName())
                + """
                    public Expression<Set<%s>> get%s() {
                        return ((Root<%s>) Root_.this.get()).get(%s_.%s);
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());

        } else if (metaMember.getMetaType().endsWith(".CollectionAttribute")) {
            return """
                    public %s_.Join_ join%s() {
                        return new %s_.Join_() {
                            @Override public CollectionJoin<%s> get() {
                                return ((Root<%s>) Root_.this.get()).join(%s_.%s);
                            }
                        };
                    }

            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                extractCollection(metaMember.getAttributeDeclarationString()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName())
                + """
                    public Expression<Collection<%s>> get%s() {
                        return ((Root<%s>) Root_.this.get()).get(%s_.%s);
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());

        } else if (metaMember.getMetaType().endsWith(".MapAttribute")) {
            return """
                    public %s_.Join_ join%s() {
                        return new %s_.Join_() {
                            @Override public MapJoin<%s> get() {
                                return ((Root<%s>) Root_.this.get()).join(%s_.%s);
                            }
                        };
                    }
            """.formatted(
                extractMapVAL(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                extractMapVAL(metaMember.getAttributeDeclarationString()),
                extractMap(metaMember.getAttributeDeclarationString()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName())
                + """
                    public Expression<Map<%s>> get%s() {
                        return ((Root<%s>) Root_.this.get()).get(%s_.%s);
                    }
            """.formatted(
                extractMapKeyVal(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());

        } else if (isEntityOrEmbeddable(metaMember.getTypeDeclaration(), context)) {
            return """
                    public %s_.Join_ join%s() {
                        return new %s_.Join_() {
                            @Override public Join<%s> get() {
                                return ((Root<%s>) Root_.this.get()).join(%s_.%s);
                            }
                        };
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                extractCollection(metaMember.getAttributeDeclarationString()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName())
                + """
                    public %s_.Path_ get%s() {
                        return new %s_.Path_() {
                            @Override public Path<%s> get() {
                                return ((Root<%s>) Root_.this.get()).get(%s_.%s);
                            }
                        };
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());

        } else {
            return """
                    public Path<%s> get%s() {
                        return ((Root<%s>) Root_.this.get()).get(%s_.%s);
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());
        }
    }


    public static String generateEnhanceJoinClass(MetaEntity entity, Context context) {
        StringBuilder sb = new StringBuilder();
        String superClassName = findMappedSuperClass(entity, context);
        if (Objects.isNull(superClassName)) {
            sb.append("    public static abstract class Join_ implements Supplier<Join<?, ? extends %s>> {"
                .formatted(entity.getSimpleName())).append('\n');
        } else {
            sb.append("    public static abstract class Join_ extends %s_.Join_ {"
                .formatted(superClassName)).append('\n');
        }
        entity.getMembers().forEach(m -> sb.append(generateEnhanceJoinMethod(entity, m, context)));
        sb.append("    }").append('\n');
        return sb.toString();
    }

    private static String generateEnhanceJoinMethod(MetaEntity entity, MetaAttribute metaMember, Context context) {

        if (metaMember.getMetaType().endsWith(".ListAttribute")) {
            return """
                    public ListJoin<%s> join%s() {
                        return ((Join<?, %s>) get()).join(%s_.%s);
                    }
            """.formatted(
                extractCollection(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName())
                + """
                    public Expression<List<%s>> get%s() {
                        return ((Join<?, %s>) get()).get(%s_.%s);
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());

        } else if (metaMember.getMetaType().endsWith(".SetAttribute")) {
            return """
                    public SetJoin<%s> join%s() {
                        return ((Join<?, %s>) get()).join(%s_.%s);
                    }
            """.formatted(
                extractCollection(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName())
                + """
                    public Expression<Set<%s>> get%s() {
                        return ((Join<?, %s>) get()).get(%s_.%s);
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());

        } else if (metaMember.getMetaType().endsWith(".CollectionAttribute")) {
            return """
                    public CollectionJoin<%s> join%s() {
                        return ((Join<?, %s>) get()).join(%s_.%s);
                    }
            """.formatted(
                extractCollection(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName())
                + """
                   public Expression<Collection<%s>> get%s() {
                        return ((Join<?, %s>) get()).get(%s_.%s);
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());

        } else if (metaMember.getMetaType().endsWith(".MapAttribute")) {
            return """
                    public MapJoin<%s> join%s() {
                        return ((Join<?, %s>) get()).join(%s_.%s);
                    }
            """.formatted(
                extractMap(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName())
                + """
                    public Expression<Map<%s>> get%s() {
                        return ((Join<?, %s>) get()).get(%s_.%s);
                    }
            """.formatted(
                extractMapKeyVal(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());

        } else if (isEntityOrEmbeddable(metaMember.getTypeDeclaration(), context)) {
            return """
                    public Join<%s> join%s() {
                        return ((Join<?, %s>) get()).join(%s_.%s);
                    }
            """.formatted(
                extractCollection(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName())
                + """
                    public Path<%s> get%s() {
                        return ((Join<?, %s>) get()).get(%s_.%s);
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());
        } else {
            return """
                    public Path<%s> get%s() {
                        return ((Join<?, %s>) get()).get(%s_.%s);
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());
        }
    }


    public static String generateEnhancePathClass(MetaEntity entity, Context context) {
        StringBuilder sb = new StringBuilder();
        String superClassName = findMappedSuperClass(entity, context);
        if (Objects.isNull(superClassName)) {
            sb.append("    public static abstract class Path_ implements Supplier<Path<? extends %s>> {"
                .formatted(entity.getSimpleName())).append('\n');
        } else {
            sb.append("    public static abstract class Path_ extends %s_.Path_ {"
                .formatted(superClassName)).append('\n');
        }
        entity.getMembers().forEach(m -> sb.append(generateEnhancePathMethod(entity, m)));
        sb.append("    }").append('\n');
        return sb.toString();
    }

    private static String generateEnhancePathMethod(MetaEntity entity, MetaAttribute metaMember) {
        if (metaMember.getMetaType().endsWith(".ListAttribute")) {
            return """
                    public Expression<List<%s>> get%s() {
                        return ((Path<%s>) get()).get(%s_.%s);
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());

        } else if (metaMember.getMetaType().endsWith(".SetAttribute")) {
            return """
                    public Expression<Set<%s>> get%s() {
                        return ((Path<%s>) get()).get(%s_.%s);
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());

        } else if (metaMember.getMetaType().endsWith(".CollectionAttribute")) {
            return """
                    public Expression<Collection<%s>> get%s() {
                        return ((Path<%s>) get()).get(%s_.%s);
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());

        } else if (metaMember.getMetaType().endsWith(".MapAttribute")) {
            return """
                    public Expression<Map<%s>> get%s() {
                        return ((Path<%s>) get()).get(%s_.%s);
                    }
            """.formatted(
                extractMapKeyVal(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());

        } else {
            return """
                    public Path<%s> get%s() {
                        return ((Path<%s>) get()).get(%s_.%s);
                    }
            """.formatted(
                extractDeclaration(metaMember.getAttributeDeclarationString()),
                capitalize(metaMember.getPropertyName()),
                entity.getSimpleName(),
                entity.getSimpleName(),
                metaMember.getPropertyName());
        }
    }


    private static Pattern COLLECTION = Pattern.compile("<(.+, .+)> .+");
    private static String extractCollection(String attributeDeclaration) {
        var matcher = COLLECTION.matcher(attributeDeclaration);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException(attributeDeclaration);
        }
    }

    private static Pattern MAP_KV = Pattern.compile("<.+, (.+, .+)> .+");
    private static String extractMapKeyVal(String attributeDeclaration) {
        var matcher = MAP_KV.matcher(attributeDeclaration);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException(attributeDeclaration);
        }
    }

    private static Pattern MAP = Pattern.compile("<(.+, .+, .+)> .+");
    private static String extractMap(String attributeDeclaration) {
        var matcher = MAP.matcher(attributeDeclaration);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException(attributeDeclaration);
        }
    }

    private static Pattern COL_DECL = Pattern.compile("<.+, (.+)> .+");
    private static String extractDeclaration(String attributeDeclaration) {
        var matcher = COL_DECL.matcher(attributeDeclaration);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException(attributeDeclaration);
        }
    }

    private static Pattern MAP_VAL = Pattern.compile("<.+, .+, (.+)> .+");
    private static String extractMapVAL(String attributeDeclaration) {
        var matcher = MAP_VAL.matcher(attributeDeclaration);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException(attributeDeclaration);
        }
    }

    private static String capitalize(String str) {
        if (Objects.isNull(str) || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static boolean isEntityOrEmbeddable(String fqcn, Context context) {
        return Objects.nonNull(context.getMetaEntity(fqcn)) ||
            Objects.nonNull(context.getMetaEmbeddable(fqcn));
    }

    private static String findMappedSuperClass(MetaEntity entity, Context context) {
        TypeMirror superClass = entity.getTypeElement().getSuperclass();
        // superclass of Object is of NoType which returns some other kind
        while (superClass.getKind() == TypeKind.DECLARED) {
            // F..king Ch...t Have those people used their horrible APIs even once?
            final Element superClassElement = ((DeclaredType) superClass).asElement();
            String superClassName = ((TypeElement) superClassElement).getQualifiedName().toString();
            if (extendsSuperMetaModel(superClassElement, entity.isMetaComplete(), context)) {
                return superClassName;
            }
            superClass = ((TypeElement) superClassElement).getSuperclass();
        }
        return null;
    }

    private static boolean extendsSuperMetaModel(Element superClassElement, boolean entityMetaComplete, Context context) {
        // if we processed the superclass in the same run we definitely need to extend
        String superClassName = ((TypeElement) superClassElement).getQualifiedName().toString();
        if (context.containsMetaEntity(superClassName)
            || context.containsMetaEmbeddable(superClassName)) {
            return true;
        }

        // to allow for the case that the metamodel class for the super entity is for example contained in another
        // jar file we use reflection. However, we need to consider the fact that there is xml configuration
        // and annotations should be ignored
        if (!entityMetaComplete && (TypeUtils.containsAnnotation( superClassElement, Constants.ENTITY)
            || TypeUtils.containsAnnotation( superClassElement, Constants.MAPPED_SUPERCLASS))) {
            return true;
        }

        return false;
    }

    protected static String generateEnhanceMethod(MetaAttribute metaMember) {
        StringBuilder sb = new StringBuilder();
        sb.append("//    " + metaMember.getAttributeDeclarationString()).append("\n");
        sb.append("//    " + metaMember.getAttributeNameDeclarationString()).append("\n");
        sb.append("//    " + metaMember.getMetaType()).append("\n");
        sb.append("//    " + metaMember.getPropertyName()).append("\n");
        sb.append("//    " + metaMember.getTypeDeclaration()).append("\n");
        sb.append("//    " + metaMember.getHostingEntity()).append("\n");
        return sb.toString();
    }
}
