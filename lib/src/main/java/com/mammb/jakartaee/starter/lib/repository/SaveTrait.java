package com.mammb.jakartaee.starter.lib.repository;

import com.mammb.jakartaee.starter.lib.data.EntityContext;

import java.io.Serializable;

public interface SaveTrait<PK extends Serializable, T> extends EntityContext<PK, T> {

    default <S extends T> S persist(S entity) {
        getEm().persist(entity);
        return entity;
    }

    default <S extends T> S merge(S entity) {
        return getEm().merge(entity);
    }

    default void flush() {
        getEm().flush();
    }

}
