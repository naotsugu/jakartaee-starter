package com.mammb.jakartaee.starter.lib.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public interface Criteria<T> extends CriteriaQueryContext<T>,
    EqTrait<T>, LikeTrait<T>, PartialLikeTrait<T>,
    GtTrait<T>, LtTrait<T>, BetweenTrait<T>,
    ExistsTrait<T>,
    ComposeTrait<T> {

    static <T> Criteria<T> of(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        return new Criteria<>() {

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
