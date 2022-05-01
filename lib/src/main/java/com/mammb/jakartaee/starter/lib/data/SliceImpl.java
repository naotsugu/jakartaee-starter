package com.mammb.jakartaee.starter.lib.data;

import java.util.Collections;
import java.util.List;

public class SliceImpl<T> implements Slice<T> {

    private final List<T> content;

    private final boolean hasNext;

    private final SlicePoint slicePoint;

    SliceImpl(List<T> content, boolean hasNext, SlicePoint slicePoint) {
        this.content = content;
        this.slicePoint = slicePoint;
        this.hasNext = hasNext;
    }

    @Override
    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    @Override
    public boolean hasNext() {
        return hasNext;
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
