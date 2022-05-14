package com.mammb.jakartaee.starter.lib.entity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class BaseEntity_Root_ implements Supplier<Root<? extends BaseEntity<?, ?>>> {

    private final Root<? extends BaseEntity<?, ?>> root;

    public BaseEntity_Root_(Root<? extends BaseEntity<?, ?>> root) {
        this.root = root;
    }

    @Override
    public Root<? extends BaseEntity<?, ?>> get() {
        return root;
    }

    public Path<Long> getVersion() {
        return BaseEntity_Root_.this.get().get(BaseEntity_.version);
    }

    public Path<LocalDateTime> getCreatedOn() {
        return BaseEntity_Root_.this.get().get(BaseEntity_.createdOn);
    }

    public Path<LocalDateTime> getLastModifiedOn() {
        return BaseEntity_Root_.this.get().get(BaseEntity_.lastModifiedOn);
    }

    public static abstract class Join_ implements Supplier<Join<?, ? extends BaseEntity<?, ?>>> {

        @Override
        public abstract Join<?, ? extends BaseEntity<?, ?>> get();

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

        @Override
        public abstract Path<? extends BaseEntity<?, ?>> get();

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
