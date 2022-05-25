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

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.Objects;

public interface ComposeTrait<T> extends CriteriaContext<T> {

    default Predicate and(Expression<Boolean> x, Expression<Boolean> y) {
        return hasNull(x, y) ? null : builder().and(x, y);
    }

    default Predicate and(Predicate... restrictions) {
        return allNull(restrictions) ? null : builder().and(restrictions);
    }

    default Predicate or(Expression<Boolean> x, Expression<Boolean> y) {
        return hasNull(x, y) ? null : builder().or(x, y);
    }

    default Predicate or(Predicate... restrictions) {
        return allNull(restrictions) ? null : builder().or(restrictions);
    }

    default Predicate not(Expression<Boolean> restriction) {
        return hasNull(restriction) ? null : builder().not(restriction);
    }

    static boolean hasNull(Object... values) {
        return Arrays.stream(values).anyMatch(Objects::isNull);
    }

    static boolean allNull(Predicate... values) {
        return Arrays.stream(values).anyMatch(Objects::isNull);
    }

}
