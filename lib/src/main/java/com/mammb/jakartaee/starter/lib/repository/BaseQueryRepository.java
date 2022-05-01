package com.mammb.jakartaee.starter.lib.repository;

import com.mammb.jakartaee.starter.lib.data.Identifiable;

import java.io.Serializable;

public abstract class BaseQueryRepository <PK extends Serializable, T extends Identifiable<PK>> extends AbstractRepository<PK, T>
    implements GetByIdTrait<PK, T>, FindAllTrait<PK, T>, FindPageTrait<PK, T>, FindSliceTrait<PK, T> {

    public BaseQueryRepository(Class<T> entityClass, Class<PK> idClass) {
        super(entityClass, idClass);
    }

}
