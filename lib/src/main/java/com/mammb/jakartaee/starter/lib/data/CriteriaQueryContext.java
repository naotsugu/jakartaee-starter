package com.mammb.jakartaee.starter.lib.data;

import jakarta.persistence.criteria.CriteriaQuery;

public interface CriteriaQueryContext<T> extends CriteriaContext<T> {

    CriteriaQuery<?> query();

}
