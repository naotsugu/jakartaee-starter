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
