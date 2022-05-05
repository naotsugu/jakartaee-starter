package com.mammb.jakartaee.starter.lib.modelgen;

import org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@SupportedAnnotationTypes({
    "jakarta.persistence.Entity", "jakarta.persistence.MappedSuperclass", "jakarta.persistence.Embeddable"
})
@SupportedOptions({
    JPAMetaModelEntityProcessor.DEBUG_OPTION,
    JPAMetaModelEntityProcessor.PERSISTENCE_XML_OPTION,
    JPAMetaModelEntityProcessor.ORM_XML_OPTION,
    JPAMetaModelEntityProcessor.FULLY_ANNOTATION_CONFIGURED_OPTION,
    JPAMetaModelEntityProcessor.LAZY_XML_PARSING,
    JPAMetaModelEntityProcessor.ADD_GENERATION_DATE,
    JPAMetaModelEntityProcessor.ADD_GENERATED_ANNOTATION,
    JPAMetaModelEntityProcessor.ADD_SUPPRESS_WARNINGS_ANNOTATION
})
public class JPAMetaModelEntityProcessorEx extends JPAMetaModelEntityProcessor {

    private static final Logger log = Logger.getLogger(JPAMetaModelEntityProcessorEx.class.getName());

    private JPAMetaModelEntityProcessor processor = new JPAMetaModelEntityProcessor();

    @Override
    public void init(ProcessingEnvironment env) {
        processor.init(env);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processor.getSupportedSourceVersion();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return processor.process(annotations, roundEnv);
    }

}
