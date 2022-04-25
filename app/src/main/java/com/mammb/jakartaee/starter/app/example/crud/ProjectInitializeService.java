package com.mammb.jakartaee.starter.app.example.crud;

import com.mammb.jakartaee.starter.domail.example.crud.Project;
import com.mammb.jakartaee.starter.domail.example.crud.ProjectState;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;

@Startup
@Singleton
public class ProjectInitializeService {

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        TypedQuery<Long> query = em.createQuery(String.format("SELECT COUNT(e) FROM %s e", Project.NAME), Long.class);
        if (query.getSingleResult() == 0) {
            em.persist(new Project(ProjectState.PLAN, "Quality Project", null, null, ""));
            em.persist(new Project(ProjectState.OPEN, "Design Project", LocalDate.of(2022, 4, 1), null, ""));
        }
    }
}
