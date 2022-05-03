package com.mammb.jakartaee.starter.lib.criteria;

import jakarta.persistence.criteria.CriteriaQuery;

public interface CriteriaQueryContext<T> extends CriteriaContext<T> {

    CriteriaQuery<?> query();

}
