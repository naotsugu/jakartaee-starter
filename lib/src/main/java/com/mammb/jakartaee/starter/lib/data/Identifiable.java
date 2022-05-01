package com.mammb.jakartaee.starter.lib.data;

import java.util.Objects;

public interface Identifiable<ID> {

	ID getId();

    default boolean hasId() {
        return Objects.isNull(getId());
    }

}
