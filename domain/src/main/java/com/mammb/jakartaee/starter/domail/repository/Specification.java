package com.mammb.jakartaee.starter.domail.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.io.Serializable;
import java.util.Objects;

public interface Specification<T> extends Serializable {

    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);

    static <T> Specification<T> not(Specification<T> spec) {
        return Objects.isNull(spec)
            ? (root, query, builder) -> null
            : (root, query, builder) -> builder.not(spec.toPredicate(root, query, builder));
    }

    static <T> Specification<T> where(Specification<T> spec) {
        return Objects.isNull(spec) ? (root, query, builder) -> null : spec;
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

            return (root, query, builder) -> {

                Predicate that  = toPredicate(lhs, root, query, builder);
                Predicate other = toPredicate(rhs, root, query, builder);

                if (Objects.isNull(that)) {
                    return other;
                }

                return Objects.isNull(other)
                    ? that
                    : combiner.combine(builder, that, other);
            };
        }

        private static <T> Predicate toPredicate(Specification<T> specification,
                Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            return Objects.isNull(specification) ? null : specification.toPredicate(root, query, cb);
        }
    }
}
