package com.mammb.jakartaee.starter.lib.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Constructor;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Criteria<T> extends CriteriaQueryContext<T>,
    EqTrait<T>, LikeTrait<T>, PartialLikeTrait<T> {

    default <R> R on(Class<R> clazz) {
        try {
            Class<?>[] types = { Root.class };
            Constructor<R> constructor = clazz.getConstructor(types);
            return constructor.newInstance(root());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static <T> Criteria<T> of(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

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
