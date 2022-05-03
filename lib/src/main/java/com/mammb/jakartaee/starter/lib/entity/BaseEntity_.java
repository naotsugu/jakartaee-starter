package com.mammb.jakartaee.starter.lib.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import java.time.LocalDateTime;

@StaticMetamodel(BaseEntity.class)
public abstract class BaseEntity_ {

    public static volatile SingularAttribute<BaseEntity, Long> version;
    public static volatile SingularAttribute<BaseEntity, LocalDateTime> createdOn;
    public static volatile SingularAttribute<BaseEntity, LocalDateTime> lastModifiedOn;

    public static final String VERSION = "version";
    public static final String CREATED_ON = "createdOn";
    public static final String LAST_MODIFIED_ON = "lastModifiedOn";

}
