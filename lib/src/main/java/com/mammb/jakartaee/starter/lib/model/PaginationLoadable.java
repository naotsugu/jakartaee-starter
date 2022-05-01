package com.mammb.jakartaee.starter.lib.model;

public interface PaginationLoadable extends Pagination {
    void load(int pageIndex);
}
