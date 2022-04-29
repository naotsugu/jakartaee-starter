package com.mammb.jakartaee.starter.app.example.auth;

import com.mammb.jakartaee.starter.domail.example.auth.Group;
import com.mammb.jakartaee.starter.domail.example.auth.User;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@Startup
@Singleton
public class UserInitializeService {

    @Inject
    private EntityManager em;

    @Inject
    private Pbkdf2PasswordHash passwordHash;


    @PostConstruct
    public void init() {
        TypedQuery<Long> query = em.createQuery(String.format("SELECT COUNT(e) FROM %s e", User.NAME), Long.class);
        if (query.getSingleResult() == 0) {

            var adminGroup = Group.of("admin");
            var userGroup = Group.of("user");
            em.persist(adminGroup);
            em.persist(userGroup);

            passwordHash.initialize(IdentityStoreConfig.HASH_PARAMS);
            var admin = User.of("admin", passwordHash.generate("admin".toCharArray()), "admin@example.com", adminGroup, userGroup);
            var user = User.of("user", passwordHash.generate("user".toCharArray()), "user@example.com", userGroup);
            em.persist(admin);
            em.persist(user);
        }
    }
}
