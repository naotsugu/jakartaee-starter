package com.mammb.jakartaee.starter.lib.criteria;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.Objects;
import java.util.regex.Pattern;

public interface PartialLikeTrait<T> extends CriteriaContext<T> {

    default Predicate partialLike(Expression<String> path, String value) {
        return isEmpty(value) ? null : builder().like(path, escapedPattern(value), '\\');
    }

    default Predicate partialLike(SingularAttribute<? super T, String> attr, String value) {
        return isEmpty(value) ? null : builder().like(root().get(attr), escapedPattern(value), '\\');
    }

    Pattern ESCAPE_PATTERN = Pattern.compile("([%_\\\\])");
    static boolean isEmpty(String value) {
        return Objects.isNull(value) || value.isEmpty();
    }
    static String escapedPattern(String str) {
        return "%" + ESCAPE_PATTERN.matcher(str).replaceAll("\\\\$1") + "%";
    }

}
