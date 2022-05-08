package com.mammb.jakartaee.starter.lib.criteria;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Objects;
import java.util.function.Function;

public interface GtTrait<T> extends CriteriaContext<T> {

    default <T1 extends Comparable<? super T1>> Predicate gt(Function<Root<T>, Expression<? extends T1>> exp, T1 value) {
        return isEmpty(value) ? null : builder().greaterThan(exp.apply(root()), value);
    }
    default <T1 extends Comparable<? super T1>> Predicate gt(Function<Root<T>, Expression<? extends T1>> exp, Expression<? extends T1> expVal) {
        return isEmpty(expVal) ? null : builder().greaterThan(exp.apply(root()), expVal);
    }
    default Predicate gt(Function<Root<T>, Expression<? extends Number>> exp, Number value) {
        return isEmpty(value) ? null : builder().gt(exp.apply(root()), value);
    }

    default <T1 extends Comparable<? super T1>> Predicate ge(Function<Root<T>, Expression<? extends T1>> exp, T1 value) {
        return isEmpty(value) ? null : builder().greaterThanOrEqualTo(exp.apply(root()), value);
    }
    default <T1 extends Comparable<? super T1>> Predicate ge(Function<Root<T>, Expression<? extends T1>> exp, Expression<? extends T1> expVal) {
        return isEmpty(expVal) ? null : builder().greaterThanOrEqualTo(exp.apply(root()), expVal);
    }
    default Predicate ge(Function<Root<T>, Expression<? extends Number>> exp, Number value) {
        return isEmpty(value) ? null : builder().ge(exp.apply(root()), value);
    }

    static boolean isEmpty(Object value) {
        return Objects.isNull(value) || (value instanceof String str) && str.isEmpty();
    }
}

