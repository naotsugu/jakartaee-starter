package com.mammb.code.jpa.modelgen.fluent;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class RootEntity extends SuperEntity {
    private String name;
    private List<ChildEntity> children;
}
