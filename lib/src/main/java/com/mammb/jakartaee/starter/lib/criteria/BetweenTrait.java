package com.mammb.jakartaee.starter.lib.criteria;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Objects;
import java.util.function.Function;

public interface BetweenTrait<T> extends CriteriaContext<T> {

    default <T1 extends Comparable<? super T1>> Predicate between(Function<Root<T>, Expression<? extends T1>> exp, T1 value1, T1 value2) {
        if (isEmpty(value1) && isEmpty(value2)) {
            return null;
        } else if (!isEmpty(value1) && !isEmpty(value2)) {
            return builder().between(exp.apply(root()), value1, value2);
        } else if (!isEmpty(value1)) {
            return builder().greaterThanOrEqualTo(exp.apply(root()), value1);
        } else if (!isEmpty(value2)) {
            return builder().lessThanOrEqualTo(exp.apply(root()), value2);
        } else {
            return null;
        }
    }

    default <T1 extends Comparable<? super T1>> Predicate between(
            Function<Root<T>, Expression<? extends T1>> exp,
            Expression<? extends T1> value1,
            Expression<? extends T1> value2) {
        if (isEmpty(value1) && isEmpty(value2)) {
            return null;
        } else if (!isEmpty(value1) && !isEmpty(value2)) {
            return builder().between(exp.apply(root()), value1, value2);
        } else if (!isEmpty(value1)) {
            return builder().greaterThanOrEqualTo(exp.apply(root()), value1);
        } else if (!isEmpty(value2)) {
            return builder().lessThanOrEqualTo(exp.apply(root()), value2);
        } else {
            return null;
        }
    }

    static boolean isEmpty(Object value) {
        return Objects.isNull(value) || (value instanceof String str) && str.isEmpty();
    }
}
