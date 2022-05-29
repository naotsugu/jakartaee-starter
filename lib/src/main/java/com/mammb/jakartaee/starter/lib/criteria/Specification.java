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

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.Objects;

public interface Specification<T> extends Serializable {


    Predicate toPredicate(Criteria<T> context);

    static <T> Specification<T> not(Specification<T> spec) {
        return Objects.isNull(spec)
            ? criteria -> null
            : context -> context.builder().not(spec.toPredicate(context));
    }

    static <T> Specification<T> where(Specification<T> spec) {
        return Objects.isNull(spec) ? context -> null : spec;
    }

    default Specification<T> and(Specification<T> other) {
        return SpecificationComposition.composed(this, other, CriteriaBuilder::and);
    }

    default Specification<T> or(Specification<T> other) {
        return SpecificationComposition.composed(this, other, CriteriaBuilder::or);
    }


    class SpecificationComposition {

        interface Combiner extends Serializable {
            Predicate combine(CriteriaBuilder builder, Predicate lhs, Predicate rhs);
        }

        static <T> Specification<T> composed(Specification<T> lhs, Specification<T> rhs, Combiner combiner) {

            return context -> {

                Predicate that  = toPredicate(lhs, context);
                Predicate other = toPredicate(rhs, context);

                if (Objects.isNull(that)) {
                    return other;
                }

                return Objects.isNull(other)
                    ? that
                    : combiner.combine(context.builder(), that, other);
            };
        }

        private static <T> Predicate toPredicate(
            Specification<T> specification, Criteria<T> context) {
            return Objects.isNull(specification) ? null : specification.toPredicate(context);
        }
    }
}
