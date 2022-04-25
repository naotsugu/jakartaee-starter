package com.mammb.jakartaee.starter.domail.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface SortSpec<T> {

    List<Order> toOrders(Root<T> root, CriteriaBuilder cb);

    default SortSpec<T> and(SortSpec<T> other) {
        return (Root<T> root, CriteriaBuilder cb) -> {
            List<Order> orders = new ArrayList<>();
            List<Order> thisOrder = this.toOrders(root, cb);
            if (Objects.nonNull(thisOrder)) {
                orders.addAll(thisOrder);
            }
            List<Order> otherOrder = other.toOrders(root, cb);
            if (Objects.nonNull(otherOrder)) {
                orders.addAll(otherOrder);
            }
            return orders;
        };
    }
}
