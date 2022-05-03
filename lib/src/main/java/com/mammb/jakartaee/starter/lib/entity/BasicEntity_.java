package com.mammb.jakartaee.starter.lib.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BasicEntity.class)
public abstract class BasicEntity_ extends BaseEntity_ {
    public static volatile SingularAttribute<BasicEntity, Long> id;
    public static final String ID = "id";
}
