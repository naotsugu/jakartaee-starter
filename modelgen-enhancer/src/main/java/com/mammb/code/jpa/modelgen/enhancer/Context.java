package com.mammb.code.jpa.modelgen.enhancer;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

public class Context {

    private final ProcessingEnvironment pe;
    private final boolean debug;

    public Context(ProcessingEnvironment pe) {
        this.pe = pe;
        this.debug = Boolean.parseBoolean(pe.getOptions().get("debug"));
    }

    public ProcessingEnvironment getProcessingEnvironment() {
        return pe;
    }

    public Elements getElementUtils() {
        return pe.getElementUtils();
    }

    public Types getTypeUtils() {
        return pe.getTypeUtils();
    }

    public void logDebug(String message) {
        if (!debug) return;
        pe.getMessager().printMessage(Diagnostic.Kind.OTHER, message);
    }
    public void logInfo(String message) {
        pe.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }
    public void logError(String message) {
        pe.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
    }

}
