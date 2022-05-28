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
package com.mammb.jakartaee.starter.lib.page;

import com.mammb.jakartaee.starter.lib.criteria.CriteriaContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.List;
import java.util.function.Function;

public interface SortContext<T> extends CriteriaContext<T> {

    static <T> SortContext<T> of(Root<T> root, CriteriaBuilder builder) {

        return new SortContext<T>() {

            @Override
            public Root<T> root() {
                return root;
            }

            @Override
            public CriteriaBuilder builder() {
                return builder;
            }
        };
    }

    default <T1> List<Order> asc(Function<Root<T>, Expression<T1>> exp) {
        return List.of(builder().asc(exp.apply(root())));
    }
    default <T1> List<Order> asc(SingularAttribute<? super T, T1> attr1) {
        return List.of(builder().asc(root().get(attr1)));
    }

    default <T1> List<Order> desc(Function<Root<T>, Expression<T1>> exp) {
        return List.of(builder().desc(exp.apply(root())));
    }
    default <T1> List<Order> desc(SingularAttribute<? super T, T1> attr1) {
        return List.of(builder().desc(root().get(attr1)));
    }

}
