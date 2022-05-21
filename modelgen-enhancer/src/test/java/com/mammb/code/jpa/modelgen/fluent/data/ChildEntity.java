package com.mammb.code.jpa.modelgen.fluent.data;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

@Entity
public class ChildEntity extends SuperEntity {

    private String name;

    @Embedded
    private ValueObject valueObject;

}
