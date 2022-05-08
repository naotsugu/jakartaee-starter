package com.mammb.code.jpa.modelgen.enhancer;

import javax.annotation.processing.FilerException;
import javax.tools.FileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

public class ClassWriter {

    private final Context context;
    private final StaticMetamodelEntity entity;
    private final Importer importer;

    public ClassWriter(Context context, StaticMetamodelEntity entity) {
        this.context = context;
        this.entity = entity;
        this.importer = Importer.of(entity.getPackageName());
    }

    public static ClassWriter of(Context context, StaticMetamodelEntity entity) {
        return new ClassWriter(context, entity);
    }

    public void writeFile() {
        try {

            String body = generateBody().toString();

            FileObject fo = context.getProcessingEnvironment().getFiler().createSourceFile(
                entity.getQualifiedName() + "Root", entity.getElement());
            PrintWriter pw = new PrintWriter(fo.openOutputStream());

            if (!entity.getPackageName().isEmpty()) {
                pw.println("package " + entity.getPackageName() + ";");
                pw.println();
            }
            pw.println(importer.generateImports());
            pw.println();

            pw.println(body);

            pw.flush();
            pw.close();

        } catch (FilerException e) {
            context.logError("Problem with Filer: " + e.getMessage());
        } catch (IOException e) {
            context.logError("Problem opening file to write MetaModel for " + entity.getSimpleName() + ": " + e.getMessage());
        }
    }


    protected StringBuffer generateBody() {
        var sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw))  {

            pw.println(generateClassDeclaration());

            return sw.getBuffer();
        }
    }

    protected String generateClassDeclaration() {
        StringBuilder sb = new StringBuilder();
        if (Objects.isNull(entity.getSuperClass())) {
            sb.append("""
                @SuppressWarnings("unchecked")
                @Generated(value = "%1$s")
                public class %2$sRoot implements Supplier<Root<? extends %3$s>> {

                    private final Root<? extends %3$s> root;
                    public %2$sRoot(Root<? extends %3$s> root) {
                        this.root = root;
                    }
                    @Override
                    public Root<? extends %3$s> get() {
                        return root;
                    }
                """.formatted(
                    JpaMetaModelEnhanceProcessor.class.getName(), // %1$s
                    entity.getSimpleName(),                       // %2$s
                    entity.getTargetEntityName()                  // %3$s
            ));
        } else {
            sb.append("""
                @SuppressWarnings("unchecked")
                @Generated(value = "%1$s")
                public class %2$sRoot extends %3$sRoot {

                    public %2$sRoot(Root<? extends %4$s> root) {
                        super(root);
                    }
                """.formatted(
                    JpaMetaModelEnhanceProcessor.class.getName(), // %1$s
                    entity.getSimpleName(),                       // %2$s
                    entity.getSuperClass(),                       // %3$s
                    entity.getTargetEntityName()                  // %4$s
            ));
        }

        entity.getAttributes().forEach(attr -> sb.append(generateRootMethod(attr)));

        sb.append(System.lineSeparator());
        sb.append(generateJoinClass()).append(System.lineSeparator());
        sb.append(generatePathClass()).append(System.lineSeparator());

        sb.append("}");
        return sb.toString();
    }


    protected String generateRootMethod(StaticMetamodelEntityAttribute attribute) {

        if (attribute.getAttributeType().isList()) {
            return """
                public %2$s_Root.Join_ join%3$s() {
                    return new %2$s_Root.Join_() {
                        @Override
                        public ListJoin<%1$s, %2$s> get() {
                            return ((Root<%1$s>) %1$s_Root.this.get()).join(%1$s_.%4$s);
                        }
                    };
                }
                public Expression<List<%2$s>> get%3$s() {
                    return ((Root<%1$s>) %1$s_Root.this.get()).get(%1$s_.%4$s);
                }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                capitalize(attribute.getName()),                      // %3$s
                attribute.getName()                                   // %4$s
            );

        } else if (attribute.getAttributeType().isSet()) {
            return """
                public %2$s_Root.Join_ join%3$s() {
                    return new %2$s_Root.Join_() {
                        @Override
                        public SetJoin<%1$s, %2$s> get() {
                            return ((Root<%1$s>) %1$s_Root.this.get()).join(%1$s_.%4$s);
                        }
                    };
                }
                public Expression<Set<%2$s>> get%3$s() {
                    return ((Root<%1$s>) %1$s_Root.this.get()).get(%1$s_.%4$s);
                }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                capitalize(attribute.getName()),                      // %3$s
                attribute.getName()                                   // %4$s
            );

        } else if (attribute.getAttributeType().isCollection()) {
            return """
                public %2$s_Root.Join_ join%3$s() {
                    return new %2$s_Root.Join_() {
                        @Override
                        public CollectionJoin<%1$s, %2$s> get() {
                            return ((Root<%1$s>) %1$s_Root.this.get()).join(%1$s_.%4$s);
                        }
                    };
                }
                public Expression<Collection<%2$s>> get%3$s() {
                    return ((Root<%1$s>) %1$s_Root.this.get()).get(%1$s_.%4$s);
                }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                capitalize(attribute.getName()),                      // %3$s
                attribute.getName()                                   // %4$s
            );

        } else if (attribute.getAttributeType().isMap()) {
            return """
                public %3$s_Root.Join_ join%4$s() {
                    return new %3$s_Root.Join_() {
                        @Override
                        public MapJoin<%1$s, %2$s, %3$s> get() {
                            return ((Root<%1$s>) %1$s_Root.this.get()).join(%1$s_.%5$s);
                        }
                    };
                }
                public Expression<Map<%2$s, %3$s>> get%4$s() {
                    return ((Root<%1$s>) %1$s_Root.this.get()).get(%1$s_.%5$s);
                }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                importer.apply(attribute.getTypeArguments().get(2)),  // %3$s
                capitalize(attribute.getName()),                      // %4$s
                attribute.getName()                                   // %5$s
            );

        } else if (attribute.getAttributeType().isSingular() && attribute.isEntityTypeTo()) {
            return """
                public %2$s_Root.Join_ join%3$s() {
                    return new %2$s_Root.Join_() {
                        @Override
                        public Join<%1$s, %2$s> get() {
                            return ((Root<%1$s>) %1$s_Root.this.get()).join(%1$s_.%4$s);
                        }
                    };
                }
                public %2$s_Root.Path_ get%3$s() {
                    return new %2$s_Root.Path_() {
                        @Override
                        public Path<%2$s> get() {
                            return ((Root<%1$s>) %1$s_Root.this.get()).get(%1$s_.%4$s);
                        }
                    };
                }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                capitalize(attribute.getName()),                      // %3$s
                attribute.getName()                                   // %4$s
            );

        } else if (attribute.getAttributeType().isSingular()) {
            return """
                public Path<%2$s> get%3$s() {
                    return ((Root<%1$s>) %1$s_Root.this.get()).get(%1$s_.%4$s);
                }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                capitalize(attribute.getName()),                      // %3$s
                attribute.getName()                                   // %4$s
            );
        } else {
            return "";
        }
    }


    protected String generateJoinClass() {

        StringBuilder sb = new StringBuilder();

        if (Objects.isNull(entity.getSuperClass())) {
            sb.append("""
                    public static abstract class Join_ implements Supplier<Join<?, ? extends %1$s>> {
                """.formatted(
                    entity.getTargetEntityName()  // %1$s
            ));
        } else {
            sb.append("""
                    public static abstract class Join_ extends %1$sRoot.Join_ {
                """.formatted(
                    entity.getSuperClass()        // %1$s
            ));
        }
        entity.getAttributes().forEach(attr -> sb.append(generateJoinMethod(attr)));

        sb.append("    }").append(System.lineSeparator());
        return sb.toString();
    }

    protected String generateJoinMethod(StaticMetamodelEntityAttribute attribute) {

        if (attribute.getAttributeType().isList()) {
            return """
                    public ListJoin<%1$s, %2$s> join%3$s() {
                        return ((Join<?, %1$s>) get()).join(%1$s_.%4$s);
                    }
                    public Expression<List<%2$s>> get%3$s() {
                        return ((Join<?, %1$s>) get()).get(%1$s_.%4$s);
                    }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                capitalize(attribute.getName()),                      // %3$s
                attribute.getName()                                   // %4$s
            );

        } else if (attribute.getAttributeType().isSet()) {
            return """
                    public SetJoin<%1$s, %2$s> join%3$s() {
                        return ((Join<?, %1$s>) get()).join(%1$s_.%4$s);
                    }
                    public Expression<Set<%2$s>> get%3$s() {
                        return ((Join<?, %1$s>) get()).get(%1$s_.%4$s);
                    }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                capitalize(attribute.getName()),                      // %3$s
                attribute.getName()                                   // %4$s
            );

        } else if (attribute.getAttributeType().isCollection()) {
            return """
                    public CollectionJoin<%1$s, %2$s> join%3$s() {
                        return ((Join<?, %1$s>) get()).join(%1$s_.%4$s);
                    }
                   public Expression<Collection<%2$s>> get%3$s() {
                        return ((Join<?, %1$s>) get()).get(%1$s_.%4$s);
                    }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                capitalize(attribute.getName()),                      // %3$s
                attribute.getName()                                   // %4$s
            );

        } else if (attribute.getAttributeType().isMap()) {
            return """
                    public MapJoin<%1$s, %2$s, %3$s> join%4$s() {
                        return ((Join<?, %1$s>) get()).join(%1$s_.%5$s);
                    }
                    public Expression<Map<%2$s, %3$s>> get%4$s() {
                        return ((Join<?, %1$s>) get()).get(%1$s_.%5$s);
                    }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                importer.apply(attribute.getTypeArguments().get(2)),  // %3$s
                capitalize(attribute.getName()),                      // %4$s
                attribute.getName()                                   // %5$s
            );

        } else if (attribute.getAttributeType().isSingular() && attribute.isEntityTypeTo()) {
            return """
                    public Join<%1$s, %2$s> join%3$s() {
                        return ((Join<?, %1$s>) get()).join(%1$s_.%4$s);
                    }
                    public Path<%2$s> get%3$s() {
                        return ((Join<?, %1$s>) get()).get(%1$s_.%4$s);
                    }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                capitalize(attribute.getName()),                      // %3$s
                attribute.getName()                                   // %4$s
            );
        } else if (attribute.getAttributeType().isSingular()) {
            return """
                    public Path<%2$s> get%3$s() {
                        return ((Join<?, %1$s>) get()).get(%1$s_.%4$s);
                    }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                capitalize(attribute.getName()),                      // %3$s
                attribute.getName()                                   // %4$s
            );
        } else {
            return "";
        }
    }

    protected String generatePathClass() {
        StringBuilder sb = new StringBuilder();

        if (Objects.isNull(entity.getSuperClass())) {
            sb.append("""
                    public static abstract class Path_ implements Supplier<Path<? extends %1$s>> {
                """.formatted(
                    entity.getTargetEntityName()  // %1$s
            ));
        } else {
            sb.append("""
                    public static abstract class Path_ extends %1$sRoot.Path_ {
                """.formatted(
                    entity.getSuperClass()        // %1$s
            ));
        }
        entity.getAttributes().forEach(attr -> sb.append(generatePathMethod(attr)));

        sb.append("    }").append(System.lineSeparator());
        return sb.toString();
    }


    protected String generatePathMethod(StaticMetamodelEntityAttribute attribute) {
        if (attribute.getAttributeType().isList()) {
            return """
                    public Expression<List<%2$s>> get%3$s() {
                        return ((Path<%1$s>) get()).get(%1$s_.%4$s);
                    }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                capitalize(attribute.getName()),                      // %3$s
                attribute.getName()                                   // %4$s
            );

        } else if (attribute.getAttributeType().isSet()) {
            return """
                    public Expression<Set<%2$s>> get%3$s() {
                        return ((Path<%1$s>) get()).get(%1$s_.%4$s);
                    }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                capitalize(attribute.getName()),                      // %3$s
                attribute.getName()                                   // %4$s
            );

        } else if (attribute.getAttributeType().isCollection()) {
            return """
                    public Expression<Collection<%2$s>> get%3$s() {
                        return ((Path<%1$s>) get()).get(%1$s_.%4$s);
                    }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                capitalize(attribute.getName()),                      // %3$s
                attribute.getName()                                   // %4$s
            );

        } else if (attribute.getAttributeType().isMap()) {
            return """
                    public Expression<Map<%2$s, %3$s>> get%4$s() {
                        return ((Path<%1$s>) get()).get(%1$s_.%5$s);
                    }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                importer.apply(attribute.getTypeArguments().get(2)),  // %3$s
                capitalize(attribute.getName()),                      // %4$s
                attribute.getName()                                   // %5$s
            );

        } else if (attribute.getAttributeType().isSingular()) {
            return """
                    public Path<%2$s> get%3$s() {
                        return ((Path<%1$s>) get()).get(%1$s_.%4$s);
                    }
            """.formatted(
                importer.apply(attribute.getTypeArguments().get(0)),  // %1$s
                importer.apply(attribute.getTypeArguments().get(1)),  // %2$s
                capitalize(attribute.getName()),                      // %3$s
                attribute.getName()                                   // %4$s
            );
        } else {
            return "";
        }

    }

    protected static String capitalize(String str) {
        if (Objects.isNull(str) || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}