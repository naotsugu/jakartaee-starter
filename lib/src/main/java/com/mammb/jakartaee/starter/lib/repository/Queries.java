package com.mammb.jakartaee.starter.lib.repository;

import com.mammb.jakartaee.starter.lib.criteria.Criteria;
import com.mammb.jakartaee.starter.lib.sort.SortContext;
import com.mammb.jakartaee.starter.lib.sort.SortSpec;
import com.mammb.jakartaee.starter.lib.criteria.Specification;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;

public class Queries {

    public static <PK extends Serializable, T> TypedQuery<T> getQuery(
        EntityContext<PK, T> context, Specification<T> spec, SortSpec<T> sort) {

        CriteriaBuilder cb = context.getEm().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(context.getEntityClass());

        Root<T> root = applySpec(context, spec, query);
        query.select(root);

        if (Objects.nonNull(sort)) {
            query.orderBy(sort.toOrders(SortContext.of(root, cb)));
        }

        return context.getEm().createQuery(query);
    }


    public static <PK extends Serializable, T> TypedQuery<Long> getCountQuery(
        EntityContext<PK, T> context, Specification<T> spec) {

        CriteriaBuilder cb = context.getEm().getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<T> root = applySpec(context, spec, query);

        if (query.isDistinct()) {
            query.select(cb.countDistinct(root));
        } else {
            query.select(cb.count(root));
        }

        query.orderBy(Collections.emptyList());

        return context.getEm().createQuery(query);
    }


    private static <S, PK extends Serializable, T> Root<T> applySpec(
        EntityContext<PK, T> context, Specification<T> spec, CriteriaQuery<S> query) {

        Root<T> root = query.from(context.getEntityClass());
        if (Objects.isNull(spec)) {
            return root;
        }

        CriteriaBuilder cb = context.getEm().getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(Criteria.of(root, query, cb));
        if (Objects.nonNull(predicate)) {
            query.where(predicate);
        }

        return root;
    }

}
