package com.mammb.jakartaee.starter.lib.data;

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

    default SliceRequest<T> with(int number) {
        return new SliceRequestImpl<>(number, getSize(), getSortSpec());
    }

    default SliceRequest<T> withSize(int size) {
        return new SliceRequestImpl<>(getNumber(), size, getSortSpec());
    }

    default SliceRequest<T> withSort(SortSpec<T> sortSpec) {
        return new SliceRequestImpl<>(getNumber(), getSize(), sortSpec);
    }
}
