package com.mammb.jakartaee.starter.domail.repository;

import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.List;

public interface FindPageTrait<PK extends Serializable, T> extends EntityContext<PK, T> {

    default Page<T> findPage(Specification<T> spec, PageRequest<T> request) {

        long count = executeCountQuery(Queries.getCountQuery(this, spec));
        if (count == 0) {
            return Page.of(List.of(), 0, request);
        }

        TypedQuery<T> query = Queries.getQuery(this, spec, request.getSortSpec());
        query.setFirstResult((int) request.getOffset());
        query.setMaxResults(request.getSize());
        return Page.of(query.getResultList(), count, request);
    }

    private static long executeCountQuery(TypedQuery<Long> query) {
        List<Long> totals = query.getResultList();
        long total = 0L;
        for (Long element : totals) {
            total += element == null ? 0 : element;
        }
        return total;
    }
}
