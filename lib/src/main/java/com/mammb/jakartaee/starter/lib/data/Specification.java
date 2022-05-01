package com.mammb.jakartaee.starter.lib.data;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.Objects;

public interface Specification<T> extends Serializable {

    Predicate toPredicate(Criteria<T> context);

    static <T> Specification<T> not(Specification<T> spec) {
        return Objects.isNull(spec)
            ? context -> null
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
