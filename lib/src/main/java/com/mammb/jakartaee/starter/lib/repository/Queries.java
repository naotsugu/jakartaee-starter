/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mammb.jakartaee.starter.lib.repository;

import com.mammb.jakartaee.starter.lib.criteria.Criteria;
import com.mammb.jakartaee.starter.lib.page.SortContext;
import com.mammb.jakartaee.starter.lib.page.SortSpec;
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
