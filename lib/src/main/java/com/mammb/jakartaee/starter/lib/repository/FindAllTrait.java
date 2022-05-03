package com.mammb.jakartaee.starter.lib.repository;

import com.mammb.jakartaee.starter.lib.sort.SortSpec;
import com.mammb.jakartaee.starter.lib.criteria.Specification;

import java.io.Serializable;
import java.util.List;

public interface FindAllTrait<PK extends Serializable, T> extends EntityContext<PK, T> {

    default List<T> findAll() {
        return Queries.getQuery(this, null, null).getResultList();
    }

    default List<T> findAll(Specification<T> spec) {
        return Queries.getQuery(this, spec, null).getResultList();
    }

    default List<T> findAll(Specification<T> spec, SortSpec<T> sort) {
        return Queries.getQuery(this, spec, sort).getResultList();
    }

}
