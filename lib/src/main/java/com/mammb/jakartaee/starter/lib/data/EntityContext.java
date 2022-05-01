package com.mammb.jakartaee.starter.lib.data;

import jakarta.persistence.EntityManager;

import java.io.Serializable;

public interface EntityContext<PK extends Serializable, T> {

    EntityManager getEm();
    Class<T> getEntityClass();
    Class<PK> getIdClass();

}
