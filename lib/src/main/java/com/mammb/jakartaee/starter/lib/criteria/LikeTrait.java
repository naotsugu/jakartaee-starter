package com.mammb.jakartaee.starter.lib.criteria;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Objects;
import java.util.regex.Pattern;

public interface LikeTrait<T> extends CriteriaContext<T> {

    default Predicate like(Path<String> path, String value) {
        return isEmpty(value) ? null : builder().like(path, escapedPattern(value), '\\');
    }

    default Predicate like(Expression<String> exp, String value) {
        return isEmpty(value) ? null : builder().like(exp, escapedPattern(value), '\\');
    }

    default Predicate like(SingularAttribute<? super T, String> attr, String value) {
        return isEmpty(value) ? null : builder().like(root().get(attr), escapedPattern(value), '\\');
    }

    Pattern ESCAPE_PATTERN = Pattern.compile("([%_\\\\])");
    static String escapedPattern(String str) {
        return ESCAPE_PATTERN.matcher(str).replaceAll("\\\\$1") + "%";
    }
    static boolean isEmpty(String value) {
        return Objects.isNull(value) || value.isEmpty();
    }

}
