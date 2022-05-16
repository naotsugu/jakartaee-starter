package com.mammb.jakartaee.starter.app.example.crud;

import com.mammb.jakartaee.starter.domail.example.crud.Plan;
import com.mammb.jakartaee.starter.domail.example.crud.PlanState;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;

@Startup
@Singleton
public class PlanInitializeService {

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        TypedQuery<Long> query = em.createQuery(String.format("SELECT COUNT(e) FROM %s e", Plan.NAME), Long.class);
        if (query.getSingleResult() == 0) {
            em.persist(new Plan(PlanState.PLAN, "Plan A", null, null, ""));
            em.persist(new Plan(PlanState.OPEN, "Plan B", LocalDate.of(2022, 4, 1), null, ""));
        }
    }
}
