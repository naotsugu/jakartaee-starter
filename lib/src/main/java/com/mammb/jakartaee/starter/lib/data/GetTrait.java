package com.mammb.jakartaee.starter.lib.data;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.metamodel.SingularAttribute;

public interface GetTrait<T> extends CriteriaContext<T> {

    default <T1> Path<T1> get(SingularAttribute<? super T, T1> attr1) {
        return root().get(attr1);
    }
    default <T1, T2> Path<T2> get(SingularAttribute<? super T, T1> attr1, SingularAttribute<? super T1, T2> attr2) {
        return root().get(attr1).get(attr2);
    }
    default <T1, T2, T3> Path<T3> get(SingularAttribute<? super T, T1> attr1, SingularAttribute<? super T1, T2> attr2, SingularAttribute<? super T2, T3> attr3) {
        return root().get(attr1).get(attr2).get(attr3);
    }
    default <T1, T2, T3, T4> Path<T4> get(SingularAttribute<? super T, T1> attr1, SingularAttribute<? super T1, T2> attr2, SingularAttribute<? super T2, T3> attr3, SingularAttribute<? super T3, T4> attr4) {
        return root().get(attr1).get(attr2).get(attr3).get(attr4);
    }
    default <T1, T2, T3, T4, T5> Path<T5> get(SingularAttribute<? super T, T1> attr1, SingularAttribute<? super T1, T2> attr2, SingularAttribute<? super T2, T3> attr3, SingularAttribute<? super T3, T4> attr4, SingularAttribute<? super T4, T5> attr5) {
        return root().get(attr1).get(attr2).get(attr3).get(attr4).get(attr5);
    }
}
