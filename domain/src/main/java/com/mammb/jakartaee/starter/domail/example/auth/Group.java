package com.mammb.jakartaee.starter.domail.example.auth;

import com.mammb.jakartaee.starter.domail.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity(name = Group.NAME)
public class Group extends BaseEntity<Group> {

    public static final String NAME = "GROUPS";

    @NotNull
    private String name;

    protected Group() {
    }
    public Group(String name) {
        this.name = name;
    }

    public static Group of(String name) {
        return new Group(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

