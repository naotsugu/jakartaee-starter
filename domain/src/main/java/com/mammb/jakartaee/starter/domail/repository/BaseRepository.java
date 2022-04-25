package com.mammb.jakartaee.starter.domail.repository;

import com.mammb.jakartaee.starter.domail.BaseEntity;
import jakarta.persistence.EntityManager;

public class BaseRepository<T extends BaseEntity<?>> extends AbstractRepository<Long, T>
    implements SaveTrait<Long, T>, GetByIdTrait<Long, T>, FindAllTrait<Long, T>, FindPageTrait<Long, T>, FindSliceTrait<Long, T> {

    public BaseRepository(EntityManager em, Class<T> entityClass) {
        super(em, entityClass, Long.class);
    }

    public void save(T entity) {
        if (entity.isNew()) {
            persist(entity);
        } else {
            merge(entity);
        }
    }

}
