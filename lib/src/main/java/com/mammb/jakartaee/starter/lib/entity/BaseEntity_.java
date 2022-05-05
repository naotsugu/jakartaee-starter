package com.mammb.jakartaee.starter.lib.entity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import java.util.function.Supplier;

@StaticMetamodel(BaseEntity.class)
public abstract class BaseEntity_ {

    public static volatile SingularAttribute<BaseEntity<?, ?>, Long> version;
    public static volatile SingularAttribute<BaseEntity<?, ?>, LocalDateTime> createdOn;
    public static volatile SingularAttribute<BaseEntity<?, ?>, LocalDateTime> lastModifiedOn;

    public static final String VERSION = "version";
    public static final String CREATED_ON = "createdOn";
    public static final String LAST_MODIFIED_ON = "lastModifiedOn";


    public static class Root_ implements Supplier<Root<? extends BaseEntity<?, ?>>> {
        private final Root<? extends BaseEntity<?, ?>> root;
        public Root_(Root<? extends BaseEntity<?, ?>> root) {
            this.root = root;
        }
        @Override
        public Root<? extends BaseEntity<?, ?>> get() {
            return root;
        }

        public Path<Long> getVersion() {
            return Root_.this.get().get(BaseEntity_.version);
        }
        public Path<LocalDateTime> getCreatedOn() {
            return Root_.this.get().get(BaseEntity_.createdOn);
        }
        public Path<LocalDateTime> getLastModifiedOn() {
            return Root_.this.get().get(BaseEntity_.lastModifiedOn);
        }

    }

    public static abstract class Join_ implements Supplier<Join<?, ? extends BaseEntity<?, ?>>> {
        public Path<Long> getVersion() {
            return get().get(BaseEntity_.version);
        }
        public Path<LocalDateTime> getCreatedOn() {
            return get().get(BaseEntity_.createdOn);
        }
        public Path<LocalDateTime> getLastModifiedOn() {
            return get().get(BaseEntity_.lastModifiedOn);
        }
    }

    public static abstract class Path_ implements Supplier<Path<? extends BaseEntity<?, ?>>> {
        public Path<Long> getVersion() {
            return get().get(BaseEntity_.version);
        }
        public Path<LocalDateTime> getCreatedOn() {
            return get().get(BaseEntity_.createdOn);
        }
        public Path<LocalDateTime> getLastModifiedOn() {
            return get().get(BaseEntity_.lastModifiedOn);
        }
    }

}
