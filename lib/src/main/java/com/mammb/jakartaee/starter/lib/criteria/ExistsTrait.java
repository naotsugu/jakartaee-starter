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
