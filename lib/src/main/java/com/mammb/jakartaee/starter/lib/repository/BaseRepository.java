package com.mammb.jakartaee.starter.lib.repository;

import com.mammb.jakartaee.starter.lib.data.Identifiable;

import java.io.Serializable;

public abstract class BaseRepository<PK extends Serializable, T extends Identifiable<PK>> extends AbstractRepository<PK, T>
    implements SaveTrait<PK, T>, GetByIdTrait<PK, T>, DeleteTrait<PK, T>,
            FindAllTrait<PK, T>, FindPageTrait<PK, T>, FindSliceTrait<PK, T> {

    public BaseRepository(Class<T> entityClass, Class<PK> idClass) {
        super(entityClass, idClass);
    }

    public void save(T entity) {
        if (entity.hasId()) {
            persist(entity);
        } else {
            merge(entity);
        }
    }

}
