package com.mammb.jakartaee.starter.lib.repository;

import java.io.Serializable;

public interface DeleteTrait<PK extends Serializable, T> extends EntityContext<PK, T> {
    default void deleteById(PK id) {
        delete(getEm().getReference(getEntityClass(), id));
    }
    default void delete(T entity) {
        getEm().remove(getEm().contains(entity) ? entity : getEm().merge(entity));
    }
}
