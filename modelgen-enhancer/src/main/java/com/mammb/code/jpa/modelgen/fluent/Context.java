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
package com.mammb.code.jpa.modelgen.fluent;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Context of metamodel enhance process.
 *
 * @author Naotsugu Kobayashi
 */
public class Context {

    /** Annotation processing environment. */
    private final ProcessingEnvironment pe;
    /** Mode of debug. */
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
