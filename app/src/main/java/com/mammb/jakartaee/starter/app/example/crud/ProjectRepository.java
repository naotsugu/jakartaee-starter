package com.mammb.jakartaee.starter.app.example.crud;

import com.mammb.jakartaee.starter.domail.example.crud.Project;
import com.mammb.jakartaee.starter.domail.example.crud.Project_;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class ProjectRepository {

    @Inject
    private EntityManager em;

    public Project get(Long id) {
        return em.find(Project.class, id);
    }

    public Project save(Project project) {
        if (project.isNew()) {
            em.persist(project);
            return project;
        } else {
            return em.merge(project);
        }
    }

    public void delete(Project project) {
        em.remove(em.find(Project.class, project.getId()));
    }

    public List<Project> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Project> query = cb.createQuery(Project.class);
        Root<Project> project = query.from(Project.class);
        query.select(project).orderBy(cb.asc(project.get(Project_.name)));
        return em.createQuery(query).getResultList();
    }

    public List<Project> findByNameLike(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Project> query = cb.createQuery(Project.class);
        Root<Project> project = query.from(Project.class);
        query.select(project);
        query.where(cb.like(project.get(Project_.name), "%" + escapeForLike(name) + "%", LIKE_ESCAPE_CHAR));
        query.orderBy(cb.asc(project.get(Project_.name)));
        return em.createQuery(query).getResultList();
    }

    private static final char LIKE_ESCAPE_CHAR = '\\';
    private static final Pattern LIKE_ESCAPE_PATTERN = Pattern.compile("([%_\\\\])");
    private static final String LIKE_ESCAPE_REPLACE = "\\\\$1";

    private static String escapeForLike(String str) {
        return LIKE_ESCAPE_PATTERN.matcher(str).replaceAll(LIKE_ESCAPE_REPLACE);
    }

}
