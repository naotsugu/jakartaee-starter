package com.mammb.jakartaee.starter.lib.data;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.List;

public interface SortContext<T> extends CriteriaContext<T> {

    static <T> SortContext<T> of (
        Root<T> root, CriteriaBuilder builder) {

        return new SortContext<T>() {

            @Override
            public Root<T> root() {
                return root;
            }

            @Override
            public CriteriaBuilder builder() {
                return builder;
            }
        };
    }

    default <T1> List<Order> asc(SingularAttribute<? super T, T1> attr1) {
        return List.of(builder().asc(root().get(attr1)));
    }
    default <T1, T2> List<Order> asc(SingularAttribute<? super T, T1> attr1, SingularAttribute<T1, T2> attr2) {
        return List.of(builder().asc(root().get(attr1).get(attr2)));
    }
    default <T1, T2, T3> List<Order> asc(SingularAttribute<? super T, T1> attr1, SingularAttribute<T1, T2> attr2, SingularAttribute<T2, T3> attr3) {
        return List.of(builder().asc(root().get(attr1).get(attr2).get(attr3)));
    }

    default <T1> List<Order> desc(SingularAttribute<? super T, T1> attr1) {
        return List.of(builder().desc(root().get(attr1)));
    }
    default <T1, T2> List<Order> desc(SingularAttribute<? super T, T1> attr1, SingularAttribute<T1, T2> attr2) {
        return List.of(builder().desc(root().get(attr1).get(attr2)));
    }
    default <T1, T2, T3> List<Order> desc(SingularAttribute<? super T, T1> attr1, SingularAttribute<T1, T2> attr2, SingularAttribute<T2, T3> attr3) {
        return List.of(builder().desc(root().get(attr1).get(attr2).get(attr3)));
    }

}
