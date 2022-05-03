package com.mammb.jakartaee.starter.lib.sort;

import jakarta.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface SortSpec<T> {

    List<Order> toOrders(SortContext<T> ctx);

    default SortSpec<T> and(SortSpec<T> other) {
        return (SortContext<T> ctx) -> {
            List<Order> orders = new ArrayList<>();
            List<Order> thisOrder = this.toOrders(ctx);
            if (Objects.nonNull(thisOrder)) {
                orders.addAll(thisOrder);
            }
            List<Order> otherOrder = other.toOrders(ctx);
            if (Objects.nonNull(otherOrder)) {
                orders.addAll(otherOrder);
            }
            return orders;
        };
    }

    static <T> SortSpec<T> empty() {
        return ctx -> List.of();
    }
}
