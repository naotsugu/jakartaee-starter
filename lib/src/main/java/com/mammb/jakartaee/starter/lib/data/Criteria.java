package com.mammb.jakartaee.starter.lib.data;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public interface Criteria<T> extends CriteriaQueryContext<T>,
    GetTrait<T>, EqTrait<T>, LikeTrait<T> {

    static <T> Criteria<T> of (
        Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        return new Criteria<T>() {

            @Override
            public Root<T> root() {
                return root;
            }

            @Override
            public CriteriaQuery<?> query() {
                return query;
            }

            @Override
            public CriteriaBuilder builder() {
                return builder;
            }
        };
    }
}
