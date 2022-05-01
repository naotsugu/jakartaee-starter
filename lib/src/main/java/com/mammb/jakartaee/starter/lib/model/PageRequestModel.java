package com.mammb.jakartaee.starter.lib.model;

import com.mammb.jakartaee.starter.lib.data.SliceRequest;
import com.mammb.jakartaee.starter.lib.data.SortSpec;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PageRequestModel<T> implements SliceRequest<T> {

    @Size
    private int number;

    @Size(min = 1)
    private int size;

    @NotNull
    private SortSpec<T> sortSpec;


    public PageRequestModel() {
        number = 0;
        size = 10;
        sortSpec = SortSpec.empty();
    }

    public PageRequestModel(int number, int size, SortSpec<T> sortSpec) {
        this.number = number;
        this.size = size;
        this.sortSpec = sortSpec;
    }

    public static <T> PageRequestModel<T> of() {
        return new PageRequestModel<>(0, 10, SortSpec.empty());
    }

    public static <T> PageRequestModel<T> of(SortSpec<T> sortSpec) {
        return new PageRequestModel<>(0, 10, sortSpec);
    }

    @Override
    public PageRequestModel<T> with(int number) {
        return new PageRequestModel<>(number, getSize(), getSortSpec());
    }

    @Override
    public PageRequestModel<T> withSize(int size) {
        return new PageRequestModel<>(getNumber(), size, getSortSpec());
    }

    @Override
    public PageRequestModel<T> withSort(SortSpec<T> sortSpec) {
        return new PageRequestModel<>(getNumber(), getSize(), sortSpec);
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

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSortSpec(SortSpec<T> sortSpec) {
        this.sortSpec = sortSpec;
    }
}
