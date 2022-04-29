package com.mammb.jakartaee.starter.app;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Dependent
public class Resources {

    @PersistenceContext(name = "default")
    private EntityManager em;

    @Produces
    private EntityManager produceEntityManager() {
        return em;
    }
}
