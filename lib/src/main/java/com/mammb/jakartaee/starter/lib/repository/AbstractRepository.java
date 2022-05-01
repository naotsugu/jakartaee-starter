package com.mammb.jakartaee.starter.lib.repository;

import com.mammb.jakartaee.starter.lib.data.EntityContext;
import jakarta.persistence.EntityManager;

import java.io.Serializable;

public abstract class AbstractRepository<PK extends Serializable, T> implements EntityContext<PK, T> {

    private final Class<T> entityClass;

    private final Class<PK> idClass;


    protected AbstractRepository(Class<T> entityClass, Class<PK> idClass) {
        this.entityClass = entityClass;
        this.idClass = idClass;
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public Class<PK> getIdClass() {
        return idClass;
    }
}
