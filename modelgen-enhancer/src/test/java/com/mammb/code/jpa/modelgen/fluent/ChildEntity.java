package com.mammb.code.jpa.modelgen.fluent;

import jakarta.persistence.Entity;

@Entity
public class ChildEntity extends SuperEntity {

    private String name;

    public String getName() {
        return name;
    }
}
