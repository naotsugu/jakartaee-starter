package com.mammb.jakartaee.starter.app.example.paging;

import com.mammb.jakartaee.starter.domail.example.paging.Employee;
import com.mammb.jakartaee.starter.lib.repository.BasicQueryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Transactional
@ApplicationScoped
public class EmployeeQueryRepository extends BasicQueryRepository<Employee> {

    @Inject
    private EntityManager em;

    protected EmployeeQueryRepository() {
        super(Employee.class);
    }

    @Override
    public EntityManager getEm() {
        return em;
    }

}
