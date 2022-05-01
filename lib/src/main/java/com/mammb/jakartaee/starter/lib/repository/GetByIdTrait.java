package com.mammb.jakartaee.starter.lib.repository;

import com.mammb.jakartaee.starter.lib.data.EntityContext;

import java.io.Serializable;
import java.util.Optional;

public interface GetByIdTrait<PK extends Serializable, T> extends EntityContext<PK, T> {
    default Optional<T> getById(PK id) {
        return Optional.ofNullable(getEm().getReference(getEntityClass(), id));
    }
}
