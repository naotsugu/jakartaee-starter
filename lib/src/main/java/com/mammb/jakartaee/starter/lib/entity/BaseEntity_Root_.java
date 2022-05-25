/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
