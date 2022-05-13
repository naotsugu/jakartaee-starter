package com.mammb.jakartaee.starter.lib.criteria;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

import java.util.Objects;

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



}
