package com.mammb.jakartaee.starter.lib.entity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

@SuppressWarnings("unchecked")
public class BasicEntity_Root_ extends BaseEntity_Root_ {

    public BasicEntity_Root_(Root<? extends BasicEntity<?>> root) {
        super(root);
    }
    public Path<Long> getId() {
        return ((Root<BasicEntity<?>>) BasicEntity_Root_.this.get()).get(BasicEntity_.id);
    }

    public static abstract class Join_ extends BaseEntity_Root_.Join_ {

        @Override
        public abstract Join<?, ? extends BasicEntity<?>> get();

        public Path<Long> getId() {
            return get().get(BasicEntity_.id);
        }
    }

    public static abstract class Path_ extends BaseEntity_Root_.Path_ {

        @Override
        public abstract Path<? extends BasicEntity<?>> get();

        public Path<Long> getId() {
            return get().get(BasicEntity_.id);
        }
    }

}
