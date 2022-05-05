package com.mammb.jakartaee.starter.lib.criteria;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Objects;

public interface EqTrait<T> extends CriteriaContext<T> {

    default Predicate eq(Path<String> path, String value) {
        return isEmpty(value) ? null : builder().equal(path, value);
    }

    default <T1> Predicate eq(SingularAttribute<? super T, T1> attr1, T1 value) {
        return builder().equal(root().get(attr1), value);
    }

    static boolean isEmpty(String value) {
        return Objects.isNull(value) || value.isEmpty();
    }
}
