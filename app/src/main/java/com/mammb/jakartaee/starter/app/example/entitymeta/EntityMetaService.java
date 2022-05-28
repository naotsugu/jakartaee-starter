package com.mammb.jakartaee.starter.app.example.entitymeta;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

@RequestScoped
public class EntityMetaService {

    @Inject
    private EntityManager em;

    public List<Table> tables() {
        return em.getMetamodel().getEntities().stream()
            .map(Table::new)
            .toList();
    }

}
