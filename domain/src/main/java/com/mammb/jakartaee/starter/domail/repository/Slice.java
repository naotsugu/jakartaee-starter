package com.mammb.jakartaee.starter.domail.repository;

import java.util.List;
import java.util.Objects;

public interface Slice<T> extends SlicePoint {

    List<T> getContent();

    default boolean hasContent() {
        return Objects.nonNull(getContent()) && getContent().size() > 0;
    }

    boolean hasNext();

    default boolean hasPrevious() {
        return getNumber() > 0;
    }

    static <T> SliceImpl<T> of(List<T> content, boolean hasNext, SlicePoint slicePoint) {
        return new SliceImpl<>(content, hasNext, slicePoint);
    }

}
