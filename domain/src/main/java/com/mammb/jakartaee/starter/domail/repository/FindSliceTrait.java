package com.mammb.jakartaee.starter.domail.repository;

import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.List;

public interface FindSliceTrait<PK extends Serializable, T> extends EntityContext<PK, T> {

    default Slice<T> findSlice(Specification<T> spec, PageRequest<T> request) {

        TypedQuery<T> query = Queries.getQuery(this, spec, request.getSortSpec());
        query.setFirstResult((int) request.getOffset());
        query.setMaxResults(request.getSize() + 1);

        List<T> result = query.getResultList();
        return (result.size() > request.getSize())
            ? Slice.of(result.subList(0, result.size() - 1), true, request)
            : Slice.of(result, false, request);
    }
}
