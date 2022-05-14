package com.mammb.jakartaee.starter.lib.criteria;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.Objects;

public interface ComposeTrait<T> extends CriteriaContext<T> {

    default Predicate and(Expression<Boolean> x, Expression<Boolean> y) {
        return hasNull(x, y) ? null : builder().and(x, y);
    }

    default Predicate and(Predicate... restrictions) {
        return allNull(restrictions) ? null : builder().and(restrictions);
    }

    default Predicate or(Expression<Boolean> x, Expression<Boolean> y) {
        return hasNull(x, y) ? null : builder().or(x, y);
    }

    default Predicate or(Predicate... restrictions) {
        return allNull(restrictions) ? null : builder().or(restrictions);
    }

    default Predicate not(Expression<Boolean> restriction) {
        return hasNull(restriction) ? null : builder().not(restriction);
    }

    static boolean hasNull(Object... values) {
        return Arrays.stream(values).anyMatch(Objects::isNull);
    }

    static boolean allNull(Predicate... values) {
        return Arrays.stream(values).anyMatch(Objects::isNull);
    }

}
