package com.mammb.jakartaee.starter.lib.page;

import java.util.List;

public interface Page<T> extends Slice<T> {

    long getTotalElements();

    default int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) getTotalElements() / (double) getSize());
    }

    static <T> Page<T> of(List<T> content, long totalElements, SlicePoint slicePoint) {
        return new PageImpl<>(content, totalElements, slicePoint);
    }

}
