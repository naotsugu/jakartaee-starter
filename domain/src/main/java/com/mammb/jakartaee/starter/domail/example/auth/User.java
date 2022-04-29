package com.mammb.jakartaee.starter.domail.example.auth;

import com.mammb.jakartaee.starter.domail.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Arrays;
import java.util.Set;

@Table(indexes = {
        @Index(name = "IDX_" + User.NAME + "_NAME", columnList = "NAME"),
        @Index(name = "IDX_" + User.NAME + "_EMAIL", columnList = "EMAIL"),
    },
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
    })
@Entity(name = User.NAME)
public class User extends BaseEntity<User> {

    public static final String NAME = "USERS";

    @NotNull
    @Size(min = 2, max = 25)
    private String name;

    @NotNull
    private String password;

    @Email
    @NotNull
    private String email;

    @ManyToMany
    private Set<Group> groups;


    protected User() {
    }

    protected User(String name, String password, String email, Set<Group> groups) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.groups = groups;
    }

    public static User of(String name, String password, String email, Group... groups) {
        return new User(name, password, email, Set.of(groups));
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<Group> getGroups() {
        return groups;
    }
}
