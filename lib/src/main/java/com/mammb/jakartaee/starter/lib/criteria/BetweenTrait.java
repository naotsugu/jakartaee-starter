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
import jakarta.persistence.criteria.Root;

import java.util.Objects;
import java.util.function.Function;

public interface BetweenTrait<T> extends CriteriaContext<T> {

    default <T1 extends Comparable<? super T1>> Predicate between(Function<Root<T>, Expression<? extends T1>> exp, T1 value1, T1 value2) {
        if (isEmpty(value1) && isEmpty(value2)) {
            return null;
        } else if (!isEmpty(value1) && !isEmpty(value2)) {
            return builder().between(exp.apply(root()), value1, value2);
        } else if (!isEmpty(value1)) {
            return builder().greaterThanOrEqualTo(exp.apply(root()), value1);
        } else if (!isEmpty(value2)) {
            return builder().lessThanOrEqualTo(exp.apply(root()), value2);
        } else {
            return null;
        }
    }

    default <T1 extends Comparable<? super T1>> Predicate between(
            Function<Root<T>, Expression<? extends T1>> exp,
            Expression<? extends T1> value1,
            Expression<? extends T1> value2) {
        if (isEmpty(value1) && isEmpty(value2)) {
            return null;
        } else if (!isEmpty(value1) && !isEmpty(value2)) {
            return builder().between(exp.apply(root()), value1, value2);
        } else if (!isEmpty(value1)) {
            return builder().greaterThanOrEqualTo(exp.apply(root()), value1);
        } else if (!isEmpty(value2)) {
            return builder().lessThanOrEqualTo(exp.apply(root()), value2);
        } else {
            return null;
        }
    }

    static boolean isEmpty(Object value) {
        return Objects.isNull(value) || (value instanceof String str) && str.isEmpty();
    }
}
