package com.mammb.jakartaee.starter.lib.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;

public interface CriteriaContext<T> {

    Root<T> root();

    CriteriaBuilder builder();

}
