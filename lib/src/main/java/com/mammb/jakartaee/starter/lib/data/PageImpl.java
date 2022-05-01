package com.mammb.jakartaee.starter.lib.data;

import java.util.Collections;
import java.util.List;

public class PageImpl<T> implements Page<T> {

    private final List<T> content;

    private final long totalElements;

    private final SlicePoint slicePoint;

    public PageImpl(List<T> content, long totalElements, SlicePoint slicePoint) {
        this.content = content;
        this.totalElements = totalElements;
        this.slicePoint = slicePoint;
    }

    @Override
    public long getTotalElements() {
        return totalElements;
    }

    @Override
    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    @Override
    public boolean hasNext() {
        return getNumber() + 1 < getTotalPages();
    }

    @Override
    public int getNumber() {
        return slicePoint.getNumber();
    }

    @Override
    public int getSize() {
        return slicePoint.getSize();
    }
}
