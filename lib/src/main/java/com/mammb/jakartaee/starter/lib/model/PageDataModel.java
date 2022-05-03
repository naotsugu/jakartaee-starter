package com.mammb.jakartaee.starter.lib.model;

import com.mammb.jakartaee.starter.lib.page.Slice;

import java.util.List;
import java.util.function.Function;

public class PageDataModel<T> implements LoadablePagination {

    private Slice<T> slice;

    private Function<Integer, Slice<T>> loader;

    protected PageDataModel(Function<Integer, Slice<T>> loader) {
        this.loader = loader;
        load(0);
    }

    public static <T> PageDataModel<T> of(Function<Integer, Slice<T>> loader) {
        return new PageDataModel<>(loader);
    }

    @Override
    public void load(int pageIndex) {
        this.slice = loader.apply(pageIndex);
    }

    public List<T> getContent() {
        return slice.getContent();
    }

    @Override
    public Slice<T> getSlice() {
        return slice;
    }

}
