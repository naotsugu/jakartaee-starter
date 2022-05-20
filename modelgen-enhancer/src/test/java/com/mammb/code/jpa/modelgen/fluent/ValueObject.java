package com.mammb.code.jpa.modelgen.fluent;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class ValueObject implements Serializable {

    private String name;
    private LocalDate localDate;

    public String getName() {
        return name;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}
