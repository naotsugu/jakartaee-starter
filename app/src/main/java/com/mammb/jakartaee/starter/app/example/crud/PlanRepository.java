package com.mammb.jakartaee.starter.app.example.crud;

import com.mammb.jakartaee.starter.domail.example.crud.Plan;
import com.mammb.jakartaee.starter.domail.example.crud.Plan_;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@ApplicationScoped
public class PlanRepository {

    @Inject
    private EntityManager em;

    public Plan get(Long id) {
        return em.find(Plan.class, id);
    }

    public Plan save(Plan plan) {
        if (plan.isNew()) {
            em.persist(plan);
            return plan;
        } else {
            return em.merge(plan);
        }
    }

    public void delete(Plan plan) {
        em.remove(em.find(Plan.class, plan.getId()));
    }

    public List<Plan> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Plan> query = cb.createQuery(Plan.class);
        Root<Plan> plan = query.from(Plan.class);
        query.select(plan).orderBy(cb.asc(plan.get(Plan_.name)));
        return em.createQuery(query).getResultList();
    }

    public List<Plan> findByNameLike(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Plan> query = cb.createQuery(Plan.class);
        Root<Plan> plan = query.from(Plan.class);
        query.select(plan);
        if (Objects.nonNull(name) && !name.isBlank()) {
            query.where(cb.like(plan.get(Plan_.name), "%" + escapeForLike(name) + "%", LIKE_ESCAPE_CHAR));
        }
        query.orderBy(cb.asc(plan.get(Plan_.name)));
        return em.createQuery(query).getResultList();
    }

    private static final char LIKE_ESCAPE_CHAR = '\\';
    private static final Pattern LIKE_ESCAPE_PATTERN = Pattern.compile("([%_\\\\])");
    private static final String LIKE_ESCAPE_REPLACE = "\\\\$1";

    private static String escapeForLike(String str) {
        return LIKE_ESCAPE_PATTERN.matcher(str).replaceAll(LIKE_ESCAPE_REPLACE);
    }

}
