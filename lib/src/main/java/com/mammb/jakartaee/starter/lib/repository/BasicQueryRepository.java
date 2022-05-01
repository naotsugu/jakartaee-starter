package com.mammb.jakartaee.starter.lib.repository;

import com.mammb.jakartaee.starter.lib.data.BasicEntity;

public abstract class BasicQueryRepository<T extends BasicEntity<?>> extends BaseQueryRepository<Long, T> {
    protected BasicQueryRepository(Class<T> entityClass) {
        super(entityClass, Long.class);
    }
}
