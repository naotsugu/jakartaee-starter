package com.mammb.code.jpa.modelgen.enhancer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Importer {

    private final Map<String, String> map;
    private final String selfPackage;

    public Importer(String selfPackage) {
        this.map = new HashMap<>();
        this.selfPackage = selfPackage;
        initImplicit();
    }

    public static Importer of(String selfPackage) {
        return new Importer(selfPackage);
    }

    private void initImplicit() {
        map.put("Expression", "jakarta.persistence.criteria.Expression");
        map.put("Root",       "jakarta.persistence.criteria.Root");
        map.put("Join",       "jakarta.persistence.criteria.Join");
        map.put("Path",       "jakarta.persistence.criteria.Path");
        map.put("ListJoin",   "jakarta.persistence.criteria.ListJoin");
        map.put("SetJoin",    "jakarta.persistence.criteria.SetJoin");
        map.put("MapJoin",    "jakarta.persistence.criteria.MapJoin");
        map.put("List",       "java.util.List");
        map.put("Map",        "java.util.Map");
        map.put("Set",        "java.util.Set");
        map.put("Supplier",   "java.util.function.Supplier");
        map.put("Generated",  "javax.annotation.processing.Generated");
    }

    protected String generateImports() {
        return map.values().stream().map(s -> "import " + s + ";")
            .sorted()
            .collect(Collectors.joining("\n"));
    }

    public String apply(String fqcn) {
        if (Objects.isNull(fqcn) || fqcn.isEmpty() || !fqcn.contains(".")) {
            return fqcn;
        }
        var simpleName = simpleName(fqcn);
        if (!map.containsKey(simpleName)) {
            if (!inPackage(fqcn)) {
                map.put(simpleName, fqcn);
            }
            return simpleName;
        } else if (map.containsKey(simpleName) && map.get(simpleName).equals(fqcn)) {
            return simpleName;
        } else {
            return fqcn;
        }
    }

    private boolean inPackage(String fqcn) {
        return (selfPackage + "." + simpleName(fqcn)).equals(fqcn);
    }

    private static String simpleName(String fqcn) {
        return fqcn.substring(fqcn.lastIndexOf('.') + 1);
    }

}
