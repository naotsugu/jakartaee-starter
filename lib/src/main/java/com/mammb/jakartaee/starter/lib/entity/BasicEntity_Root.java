package com.mammb.jakartaee.starter.lib.entity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

@SuppressWarnings("unchecked")
public class BasicEntity_Root extends BaseEntity_Root {

    public BasicEntity_Root(Root<? extends BasicEntity<?>> root) {
        super(root);
    }
    public Path<Long> getId() {
        return ((Root<BasicEntity<?>>) BasicEntity_Root.this.get()).get(BasicEntity_.id);
    }

    public static abstract class Join_ extends BaseEntity_.Join_ {
        public Path<Long> getId() {
            return ((Join<?, BasicEntity<?>>) get()).get(BasicEntity_.id);
        }
    }

    public static abstract class Path_ extends BaseEntity_.Path_ {
        public Path<Long> getId() {
            return ((Path<BasicEntity<?>>) get()).get(BasicEntity_.id);
        }
    }

}
