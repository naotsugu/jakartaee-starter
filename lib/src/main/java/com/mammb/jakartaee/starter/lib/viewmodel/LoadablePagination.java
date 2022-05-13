package com.mammb.jakartaee.starter.lib.viewmodel;

public interface LoadablePagination extends Pagination {
    void load(int pageIndex);
}
