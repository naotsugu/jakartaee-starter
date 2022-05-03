package com.mammb.jakartaee.starter.lib.page;

public interface SlicePoint {

    int getNumber();

    int getSize();

    default long getOffset() {
        return (long) getNumber() * (long) getSize();
    }

}
