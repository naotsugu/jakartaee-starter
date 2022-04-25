package com.mammb.jakartaee.starter.domail.repository;

import com.mammb.jakartaee.starter.domail.BaseEntity;
import jakarta.persistence.EntityManager;

public class QueryRepository<T extends BaseEntity<?>> extends AbstractRepository<Long, T>
    implements GetByIdTrait<Long, T>, FindAllTrait<Long, T>, FindPageTrait<Long, T>, FindSliceTrait<Long, T> {

    public QueryRepository(EntityManager em, Class<T> entityClass) {
        super(em, entityClass, Long.class);
    }

}
