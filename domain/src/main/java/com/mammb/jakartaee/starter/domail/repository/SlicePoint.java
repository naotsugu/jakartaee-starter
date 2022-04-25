package com.mammb.jakartaee.starter.domail.repository;

public interface SlicePoint {

    int getNumber();

    int getSize();

    default long getOffset() {
        return (long) getNumber() * (long) getSize();
    }

}
