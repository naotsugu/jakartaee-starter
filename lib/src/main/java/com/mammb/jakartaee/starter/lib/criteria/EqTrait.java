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
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.Objects;
import java.util.function.Function;

public interface EqTrait<T> extends CriteriaContext<T> {

    default Predicate eq(Path<String> path, String value) {
        return isEmpty(value) ? null : builder().equal(path, value);
    }

    default Predicate eq(Expression<?> exp, Object value) {
        return isEmpty(value) ? null : builder().equal(exp, value);
    }

    default <T1> Predicate eq(SingularAttribute<? super T, T1> attr1, T1 value) {
        return isEmpty(value) ? null : builder().equal(root().get(attr1), value);
    }

    default <T1> Predicate eq(Function<Root<T>, Path<? extends T1>> exp, T1 value) {
        return isEmpty(value) ? null : builder().equal(exp.apply(root()), value);
    }

    default Predicate eq(Expression<?> x, Expression<?> y) {
        return (isEmpty(x) || isEmpty(y)) ? null : builder().equal(x, y);
    }

    static boolean isEmpty(Object value) {
        return Objects.isNull(value) || (value instanceof String str) && str.isEmpty();
    }
}
