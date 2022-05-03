package com.mammb.jakartaee.starter.lib.page;

import com.mammb.jakartaee.starter.lib.sort.SortSpec;
import com.mammb.jakartaee.starter.lib.sort.Sortable;

public interface SliceRequest<T> extends SlicePoint, Sortable<T> {

    static <T> SliceRequestImpl<T> of(int number, int size, SortSpec<T> sortSpec) {
        return new SliceRequestImpl<>(number, size, sortSpec);
    }

    static <T> SliceRequestImpl<T> of(int number, int size) {
        return new SliceRequestImpl<>(number, size, null);
    }

    static <T> SliceRequestImpl<T> of(int number, SortSpec<T> sortSpec) {
        return new SliceRequestImpl<>(number, 10, sortSpec);
    }

}
