package com.mammb.jakartaee.starter.domail.repository;

import java.io.Serializable;
import java.util.Optional;

public interface GetByIdTrait<PK extends Serializable, T> extends EntityContext<PK, T> {
    default Optional<T> getById(PK id) {
        return Optional.ofNullable(getEm().getReference(getEntityClass(), id));
    }
}
