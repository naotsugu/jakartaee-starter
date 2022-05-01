package com.mammb.jakartaee.starter.domail.example.book;

import com.mammb.jakartaee.starter.domail.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity(name = Author.NAME)
public class Author extends BaseEntity<Author> {

    public static final String NAME = "AUTHORS";

    @NotBlank
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    protected Author() {
    }

    protected Author(String name) {
        this.name = name;
    }

    public static Author of(String name) {
        return new Author(name);
    }

    public String getName() {
        return name;
    }
}
