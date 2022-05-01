package com.mammb.jakartaee.starter.lib.repository;

import com.mammb.jakartaee.starter.lib.data.EntityContext;
import com.mammb.jakartaee.starter.lib.data.SliceRequest;
import com.mammb.jakartaee.starter.lib.data.Slice;
import com.mammb.jakartaee.starter.lib.data.Specification;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.List;

public interface FindSliceTrait<PK extends Serializable, T> extends EntityContext<PK, T> {

    default Slice<T> findSlice(SliceRequest<T> request, Specification<T> spec) {

        TypedQuery<T> query = Queries.getQuery(this, spec, request.getSortSpec());
        query.setFirstResult((int) request.getOffset());
        query.setMaxResults(request.getSize() + 1);

        List<T> result = query.getResultList();
        return (result.size() > request.getSize())
            ? Slice.of(result.subList(0, result.size() - 1), true, request)
            : Slice.of(result, false, request);
    }
}
