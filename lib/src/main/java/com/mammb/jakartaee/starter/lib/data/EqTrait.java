package com.mammb.jakartaee.starter.lib.data;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.metamodel.SingularAttribute;

public interface EqTrait<T> extends CriteriaContext<T> {
    default <T1> Predicate eq(T1 value, SingularAttribute<? super T, T1> attr1) {
        return builder().equal(root().get(attr1), value);
    }
    default <T1, T2> Predicate eq(T2 value, SingularAttribute<? super T, T1> attr1, SingularAttribute<T1, T2> attr2) {
        return builder().equal(root().get(attr1).get(attr2), value);
    }
    default <T1, T2, T3> Predicate eq(T3 value, SingularAttribute<? super T, T1> attr1, SingularAttribute<T1, T2> attr2, SingularAttribute<T2, T3> attr3) {
        return builder().equal(root().get(attr1).get(attr2).get(attr3), value);
    }
    default <T1, T2, T3, T4> Predicate eq(T4 value, SingularAttribute<? super T, T1> attr1, SingularAttribute<T1, T2> attr2, SingularAttribute<T2, T3> attr3, SingularAttribute<T3, T4> attr4) {
        return builder().equal(root().get(attr1).get(attr2).get(attr3), value);
    }
}
