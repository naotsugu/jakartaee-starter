package com.mammb.jakartaee.starter.app.example.ejb;

import com.mammb.jakartaee.starter.domail.example.ejb.Customer;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Startup
@Singleton
public class CustomerInitializeService {

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        TypedQuery<Long> query = em.createQuery(String.format("SELECT COUNT(e) FROM %s e", Customer.NAME), Long.class);
        if (query.getSingleResult() == 0) {
            em.persist(new Customer("George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023"));
            em.persist(new Customer("Betty", "Davis", "638 Cardinal Ave.", "Sun Prairie", "6085551749"));
            em.persist(new Customer("Eduardo", "Rodriquez", "2693 Commerce St.", "McFarland", "6085558763"));
            em.persist(new Customer("Harold", "Davis", "563 Friendly St.", "Windsor", "6085553198"));
        }
    }

}
