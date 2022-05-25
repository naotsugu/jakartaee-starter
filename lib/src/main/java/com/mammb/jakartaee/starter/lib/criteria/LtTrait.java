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

public interface LtTrait <T> extends CriteriaContext<T> {

    default <T1 extends Comparable<? super T1>> Predicate lt(Function<Root<T>, Expression<? extends T1>> exp, T1 value) {
        return isEmpty(value) ? null : builder().lessThan(exp.apply(root()), value);
    }
    default <T1 extends Comparable<? super T1>> Predicate lt(Function<Root<T>, Expression<? extends T1>> exp, Expression<? extends T1> expVal) {
        return isEmpty(expVal) ? null : builder().lessThan(exp.apply(root()), expVal);
    }

    default <T1 extends Comparable<? super T1>> Predicate le(Function<Root<T>, Expression<? extends T1>> exp, T1 value) {
        return isEmpty(value) ? null : builder().lessThanOrEqualTo(exp.apply(root()), value);
    }
    default <T1 extends Comparable<? super T1>> Predicate le(Function<Root<T>, Expression<? extends T1>> exp, Expression<? extends T1> expVal) {
        return isEmpty(expVal) ? null : builder().lessThanOrEqualTo(exp.apply(root()), expVal);
    }

    static boolean isEmpty(Object value) {
        return Objects.isNull(value) || (value instanceof String str) && str.isEmpty();
    }
}
