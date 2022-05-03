package com.mammb.jakartaee.starter.lib.page;

import com.mammb.jakartaee.starter.lib.sort.SortSpec;

import java.util.Objects;

public class SliceRequestImpl<T> implements SliceRequest<T> {

    private final int number;
    private final int size;
    private final SortSpec<T> sortSpec;

    SliceRequestImpl(int number, int size, SortSpec<T> sortSpec) {
        if (number < 0) {
            throw new IllegalArgumentException("Page number must not be less than zero!");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }
        this.number = number;
        this.size = size;
        this.sortSpec = Objects.isNull(sortSpec) ? SortSpec.empty() : sortSpec;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public SortSpec<T> getSortSpec() {
        return sortSpec;
    }
}
