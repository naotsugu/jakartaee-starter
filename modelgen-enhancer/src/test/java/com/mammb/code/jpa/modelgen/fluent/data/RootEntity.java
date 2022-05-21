package com.mammb.code.jpa.modelgen.fluent.data;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class RootEntity extends SuperEntity {
    private String name;
    @OneToMany
    private List<ChildEntity> childrenList;
    @OneToMany
    private Set<ChildEntity> childrenSet;
    @OneToMany
    private Collection<ChildEntity> childrenCollection;
    @OneToMany
    private Map<String, ChildEntity> childrenMap;
}
