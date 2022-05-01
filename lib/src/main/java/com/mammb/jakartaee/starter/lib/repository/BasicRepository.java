package com.mammb.jakartaee.starter.lib.repository;

import com.mammb.jakartaee.starter.lib.data.BasicEntity;

public abstract class BasicRepository<T extends BasicEntity<?>> extends BaseRepository<Long, T> {
    protected BasicRepository(Class<T> entityClass) {
        super(entityClass, Long.class);
    }
}
