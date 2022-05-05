package com.mammb.jakartaee.starter.domail.example.criteria;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Inhel extends RootItem {

    private String hoo;

    @ManyToOne
    private ChileItem ci;

    public String getHoo() {
        return hoo;
    }

    public ChileItem getCi() {
        return ci;
    }
}
