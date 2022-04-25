package com.mammb.jakartaee.starter.domail.repository;

public interface PageRequest<T> extends SlicePoint {

    SortSpec<T> getSortSpec();

    static <T> PageRequestImpl<T> of(int number, int size, SortSpec<T> sortSpec) {
        return new PageRequestImpl<>(number, size, sortSpec);
    }

    static <T> PageRequestImpl<T> of(int number, int size) {
        return new PageRequestImpl<>(number, size, null);
    }

    static <T> PageRequestImpl<T> of(int number, SortSpec<T> sortSpec) {
        return new PageRequestImpl<>(number, 10, sortSpec);
    }

}
