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
package com.mammb.jakartaee.starter.lib.criteria;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

import java.util.Objects;
import java.util.function.Function;

public interface ExistsTrait<T> extends CriteriaQueryContext<T> {

    default Predicate exists(Subquery<?> subquery) {
        return builder().exists(subquery);
    }

    default Predicate exists(Specification<T> spec) {
        Subquery<T> subquery = query().subquery(root().getModel().getJavaType());
        Root<T> correlateRoot = subquery.correlate(root());
        Predicate predicate = spec.toPredicate(Criteria.of(correlateRoot, query(), builder()));
        return Objects.isNull(predicate) ? null : builder().exists(subquery.where(predicate));
    }

    default <U> Predicate exists(Class<U> ent, Function<Root<U>, Predicate> exp) {
        Subquery<U> subquery = query().subquery(ent);
        Root<U> correlateRoot = subquery.from(ent);
        Predicate predicate = exp.apply(correlateRoot);
        return Objects.isNull(predicate) ? null : builder().exists(subquery.where(predicate));
    }

}
