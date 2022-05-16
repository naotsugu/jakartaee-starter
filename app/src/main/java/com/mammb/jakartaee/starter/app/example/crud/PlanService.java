package com.mammb.jakartaee.starter.app.example.crud;

import com.mammb.jakartaee.starter.domail.example.crud.Plan;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
@RequestScoped
public class PlanService {

    @Inject
    private PlanRepository repository;

    public List<Plan> findAll(String name) {
        return repository.findByNameLike(name);
    }

    public Long save(Plan plan) {
        repository.save(plan);
        return plan.getId();
    }

    public void update(Plan plan) {
        repository.save(plan);
    }

    public void delete(Plan plan) {
        repository.delete(plan);
    }

}
