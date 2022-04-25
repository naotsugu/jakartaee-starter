package com.mammb.jakartaee.starter.domail.repository;

import java.io.Serializable;

public interface Delete<PK extends Serializable, T> extends EntityContext<PK, T> {
    default void deleteById(PK id) {
        delete(getEm().getReference(getEntityClass(), id));
    }
    default void delete(T entity) {
        getEm().remove(getEm().contains(entity) ? entity : getEm().merge(entity));
    }
}
