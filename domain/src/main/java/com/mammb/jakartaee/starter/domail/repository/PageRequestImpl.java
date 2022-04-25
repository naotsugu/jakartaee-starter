package com.mammb.jakartaee.starter.domail.repository;

public class PageRequestImpl<T> implements PageRequest<T> {

    private final int number;
    private final int size;
    private final SortSpec<T> sortSpec;

    PageRequestImpl(int number, int size, SortSpec<T> sortSpec) {
        if (number < 0) {
            throw new IllegalArgumentException("Page number must not be less than zero!");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }
        this.number = number;
        this.size = size;
        this.sortSpec = sortSpec;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public int getSize() {
        return size;
    }

    public SortSpec<T> getSortSpec() {
        return sortSpec;
    }

}
