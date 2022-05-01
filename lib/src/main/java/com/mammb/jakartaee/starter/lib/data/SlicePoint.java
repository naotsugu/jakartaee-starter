package com.mammb.jakartaee.starter.lib.data;

public interface SlicePoint {

    int getNumber();

    int getSize();

    default long getOffset() {
        return (long) getNumber() * (long) getSize();
    }

}
