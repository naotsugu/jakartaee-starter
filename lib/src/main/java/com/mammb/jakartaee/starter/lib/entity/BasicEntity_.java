package com.mammb.jakartaee.starter.lib.entity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BasicEntity.class)
public abstract class BasicEntity_ extends BaseEntity_ {

    public static volatile SingularAttribute<BasicEntity<?>, Long> id;
    public static final String ID = "id";

    public static class Root_ extends BaseEntity_.Root_ {
        public Root_(Root<? extends BasicEntity<?>> root) {
            super(root);
        }
        @SuppressWarnings("unchecked")
        public Path<Long> getId() {
            return ((Root<BasicEntity<?>>) Root_.this.get()).get(BasicEntity_.id);
        }
    }

    public static abstract class Join_ extends BaseEntity_.Join_ {
        @SuppressWarnings("unchecked")
        public Path<Long> getId() {
            return ((Join<?, BasicEntity<?>>) get()).get(BasicEntity_.id);
        }
    }

    public static abstract class Path_ extends BaseEntity_.Path_ {
        @SuppressWarnings("unchecked")
        public Path<Long> getId() {
            return ((Path<BasicEntity<?>>) get()).get(BasicEntity_.id);
        }
    }
}
