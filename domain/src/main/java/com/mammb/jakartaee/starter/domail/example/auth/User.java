package com.mammb.jakartaee.starter.domail.example.auth;

import com.mammb.jakartaee.starter.domail.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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

    @Email
    @NotNull
    private String email;

    protected User() {
    }

    protected User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static User of(String name, String email) {
        return new User(name, email);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
