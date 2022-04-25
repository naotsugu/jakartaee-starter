package com.mammb.jakartaee.starter.app.example.auth;

import com.mammb.jakartaee.starter.domail.example.auth.User;
import com.mammb.jakartaee.starter.domail.example.auth.User_;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

@ApplicationScoped
public class UserRepository {

    @Inject
    private EntityManager em;

    public User get(Long id) {
        return em.find(User.class, id);
    }

    public User persist(User user) {
        em.persist(user);
        return user;
    }

    public void delete(User user) {
        em.remove(em.find(User.class, user.getId()));
    }

    public List<User> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class);
        query.select(user).orderBy(cb.asc(user.get(User_.name)));
        return em.createQuery(query).getResultList();
    }
}
