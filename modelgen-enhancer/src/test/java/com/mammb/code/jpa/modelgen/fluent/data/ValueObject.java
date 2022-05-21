package com.mammb.code.jpa.modelgen.fluent.data;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class ValueObject implements Serializable {

    private String name;

    private LocalDate localDate;

    @Enumerated(EnumType.STRING)
    private EnumValue enumValue;

}
