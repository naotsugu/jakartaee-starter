package com.mammb.jakartaee.starter.lib.data;

public interface Sortable<T> {
    SortSpec<T> getSortSpec();
}
