package com.mammb.jakartaee.starter.app.example.ejb;

import com.mammb.jakartaee.starter.domail.example.ejb.Customer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

@Stateless
public class CustomerServiceImpl implements CustomerService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long save(Customer customer) {
        em.persist(customer);
        return customer.getId();
    }

    @Override
    public void update(Customer customer) {
        em.merge(customer);
    }

    @Override
    public List<Customer> findAll() {
        CriteriaQuery<Customer> query = em.getCriteriaBuilder().createQuery(Customer.class);
        Root<Customer> customer = query.from(Customer.class);
        query.select(customer);
        return em.createQuery(query).getResultList();
    }

}
