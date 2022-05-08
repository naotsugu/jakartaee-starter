package com.mammb.code.jpa.modelgen.enhancer;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Objects;
import java.util.Set;

@SupportedAnnotationTypes({
    JpaMetaModelEnhanceProcessor.STATIC_METAMODEL
})
public class JpaMetaModelEnhanceProcessor extends AbstractProcessor {

    public static final String STATIC_METAMODEL = "jakarta.persistence.metamodel.StaticMetamodel";

    private Context context;


    @Override
    public void init(ProcessingEnvironment env) {
        super.init(env);
        this.context = new Context(env);

        var version = getClass().getPackage().getImplementationVersion();
        context.logInfo("JPA Static-Metamodel Enhance Generator " + (Objects.isNull(version) ? "" : version));
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver() || annotations.isEmpty()) {
            return false;
        }

        roundEnv.getRootElements().stream()
            .filter(this::isStaticMetamodel)
            .map(TypeElement.class::cast)
            .map(this::asStaticMetamodelEntity)
            .forEach(this::createMetaModelClasses);

        return false;
    }


    private StaticMetamodelEntity asStaticMetamodelEntity(final TypeElement element) {
        context.logDebug("Processing annotated class " + element.toString());
        return StaticMetamodelEntity.of(context, element);
    }


    protected void createMetaModelClasses(StaticMetamodelEntity entity) {
        context.logDebug(entity.toString());
        new ClassWriter(context, entity).writeFile();
    }


    private boolean isStaticMetamodel(Element element) {
        return ElementKind.CLASS == element.getKind() &&
            element.getAnnotationMirrors().stream()
                .map(AnnotationMirror::getAnnotationType)
                .map(Object::toString)
                .anyMatch(STATIC_METAMODEL::equals);
    }

}
