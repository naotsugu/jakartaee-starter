package com.mammb.jakartaee.starter.domail.repository;

import jakarta.persistence.EntityManager;

import java.io.Serializable;

public abstract class AbstractRepository<PK extends Serializable, T> implements EntityContext<PK, T> {

    private final EntityManager em;

    private final Class<T> entityClass;

    private final Class<PK> idClass;


    protected AbstractRepository(EntityManager em, Class<T> entityClass, Class<PK> idClass) {
        this.em = em;
        this.entityClass = entityClass;
        this.idClass = idClass;
    }

    @Override
    public EntityManager getEm() {
        return em;
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
