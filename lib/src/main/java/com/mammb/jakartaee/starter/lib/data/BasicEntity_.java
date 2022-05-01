package com.mammb.jakartaee.starter.lib.data;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BasicEntity.class)
public class BasicEntity_ {

    public static volatile SingularAttribute<BasicEntity, Long> id;
    public static volatile SingularAttribute<BasicEntity, Long> version;

    public static final String ID = "id";
    public static final String VERSION = "version";

}
