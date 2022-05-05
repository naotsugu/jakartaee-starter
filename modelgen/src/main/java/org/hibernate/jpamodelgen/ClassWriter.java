package org.hibernate.jpamodelgen;

import org.hibernate.jpamodelgen.model.MetaAttribute;
import org.hibernate.jpamodelgen.model.MetaEntity;
import org.hibernate.jpamodelgen.util.Constants;
import org.hibernate.jpamodelgen.util.TypeUtils;

import javax.annotation.processing.FilerException;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class ClassWriter {

    private static final String META_MODEL_CLASS_NAME_SUFFIX = "_";

    private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSSZ" );
        }
    };

    private ClassWriter() {
    }

    public static void writeFile(MetaEntity entity, Context context) {
        try {
            String metaModelPackage = entity.getPackageName();
            // need to generate the body first, since this will also update the required imports which need to
            // be written out first
            String body = generateBody(entity, context).toString();

            FileObject fo = context.getProcessingEnvironment().getFiler().createSourceFile(
                getFullyQualifiedClassName(entity, metaModelPackage),
                entity.getTypeElement()
            );
            OutputStream os = fo.openOutputStream();
            PrintWriter pw = new PrintWriter(os);

            if (!metaModelPackage.isEmpty()) {
                pw.println( "package " + metaModelPackage + ";" );
                pw.println();
            }
            pw.println(entity.generateImports());
            pw.println(generateEnhanceImports());
            pw.println(body);

            pw.flush();
            pw.close();

        } catch (FilerException filerEx) {
            context.logMessage(
                Diagnostic.Kind.ERROR, "Problem with Filer: " + filerEx.getMessage()
            );
        } catch (IOException ioEx) {
            context.logMessage(
                Diagnostic.Kind.ERROR,
                "Problem opening file to write MetaModel for " + entity.getSimpleName() + ": " + ioEx.getMessage()
            );
        }
    }

    /**
     * Generate everything after import statements.
     *
     * @param entity The meta entity for which to write the body
     * @param context The processing context
     *
     * @return body content
     */
    private static StringBuffer generateBody(MetaEntity entity, Context context) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(sw);

            if (context.addGeneratedAnnotation()) {
                pw.println( writeGeneratedAnnotation(entity, context));
            }
            if (context.isAddSuppressWarningsAnnotation()) {
                pw.println(writeSuppressWarnings());
            }

            pw.println(writeStaticMetaModelAnnotation(entity));

            printClassDeclaration(entity, pw, context);

            pw.println();

            List<MetaAttribute> members = entity.getMembers();
            for (MetaAttribute metaMember : members) {
                pw.println("    " + metaMember.getAttributeDeclarationString());
            }
            pw.println();
            for (MetaAttribute metaMember : members) {
                pw.println("    " + metaMember.getAttributeNameDeclarationString());
            }

            pw.println();
            pw.println("    // -- Enhance -------------------------------------------------------------");
            pw.println();
            pw.println(generateEnhanceRootClass(entity, context));
            pw.println(generateEnhanceJoinClass(entity, context));
            pw.println(generateEnhancePathClass(entity, context));

            pw.println();
            pw.println("}");
            return sw.getBuffer();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    private static void printClassDeclaration(MetaEntity entity, PrintWriter pw, Context context) {
        pw.print( "public abstract class " + entity.getSimpleName() + META_MODEL_CLASS_NAME_SUFFIX );

        String superClassName = findMappedSuperClass(entity, context);
        if (superClassName != null) {
            pw.print(" extends " + superClassName + META_MODEL_CLASS_NAME_SUFFIX);
        }

        pw.println(" {");
    }

    private static String findMappedSuperClass(MetaEntity entity, Context context) {
        TypeMirror superClass = entity.getTypeElement().getSuperclass();
        //superclass of Object is of NoType which returns some other kind
        while ( superClass.getKind() == TypeKind.DECLARED ) {
            //F..king Ch...t Have those people used their horrible APIs even once?
            final Element superClassElement = ( (DeclaredType) superClass ).asElement();
            String superClassName = ( (TypeElement) superClassElement ).getQualifiedName().toString();
            if ( extendsSuperMetaModel( superClassElement, entity.isMetaComplete(), context ) ) {
                return superClassName;
            }
            superClass = ( (TypeElement) superClassElement ).getSuperclass();
        }
        return null;
    }

    /**
     * Checks whether this metamodel class needs to extend another metamodel class.
     * This methods checks whether the processor has generated a metamodel class for the super class, but it also
     * allows for the possibility that the metamodel class was generated in a previous compilation (eg it could be
     * part of a separate jar. See also METAGEN-35).
     *
     * @param superClassElement the super class element
     * @param entityMetaComplete flag indicating if the entity for which the metamodel should be generarted is metamodel
     * complete. If so we cannot use reflection to decide whether we have to add the extend clause
     * @param context the execution context
     *
     * @return {@code true} in case there is super class meta model to extend from {@code false} otherwise.
     */
    private static boolean extendsSuperMetaModel(Element superClassElement, boolean entityMetaComplete, Context context) {
        // if we processed the superclass in the same run we definitely need to extend
        String superClassName = ( (TypeElement) superClassElement ).getQualifiedName().toString();
        if ( context.containsMetaEntity( superClassName )
            || context.containsMetaEmbeddable( superClassName ) ) {
            return true;
        }

        // to allow for the case that the metamodel class for the super entity is for example contained in another
        // jar file we use reflection. However, we need to consider the fact that there is xml configuration
        // and annotations should be ignored
        if ( !entityMetaComplete && ( TypeUtils.containsAnnotation( superClassElement, Constants.ENTITY )
            || TypeUtils.containsAnnotation( superClassElement, Constants.MAPPED_SUPERCLASS ) ) ) {
            return true;
        }

        return false;
    }

    private static String getFullyQualifiedClassName(MetaEntity entity, String metaModelPackage) {
        String fullyQualifiedClassName = "";
        if ( !metaModelPackage.isEmpty() ) {
            fullyQualifiedClassName = fullyQualifiedClassName + metaModelPackage + ".";
        }
        fullyQualifiedClassName = fullyQualifiedClassName + entity.getSimpleName() + META_MODEL_CLASS_NAME_SUFFIX;
        return fullyQualifiedClassName;
    }

    private static String writeGeneratedAnnotation(MetaEntity entity, Context context) {
        StringBuilder generatedAnnotation = new StringBuilder();
        generatedAnnotation.append( "@" )
            .append( entity.importType( context.getGeneratedAnnotationFqcn() ) )
            .append( "(value = \"" )
            .append( JPAMetaModelEntityProcessor.class.getName() );
        if ( context.addGeneratedDate() ) {
            generatedAnnotation.append( "\", date = \"" )
                .append( SIMPLE_DATE_FORMAT.get().format( new Date() ) )
                .append( "\")" );
        }
        else {
            generatedAnnotation.append( "\")" );
        }
        return generatedAnnotation.toString();
    }

    private static String writeSuppressWarnings() {
        return "@SuppressWarnings({ \"deprecation\", \"rawtypes\" })";
    }

    private static String writeStaticMetaModelAnnotation(MetaEntity entity) {
        return "@" + entity.importType( "jakarta.persistence.metamodel.StaticMetamodel" ) + "(" + entity.getSimpleName() + ".class)";
    }


    protected static String generateEnhanceImports() {
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


    private static String generateEnhanceRootClass(MetaEntity entity, Context context) {
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
                                return ((Root<%s>) Root_.this.get()).join(%s_.%s);
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


    private static String generateEnhanceJoinClass(MetaEntity entity, Context context) {
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


    private static String generateEnhancePathClass(MetaEntity entity, Context context) {
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

}
