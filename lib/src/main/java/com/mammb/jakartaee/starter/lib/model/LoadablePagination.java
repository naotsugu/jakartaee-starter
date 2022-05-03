package com.mammb.jakartaee.starter.lib.model;

public interface LoadablePagination extends Pagination {
    void load(int pageIndex);
}
