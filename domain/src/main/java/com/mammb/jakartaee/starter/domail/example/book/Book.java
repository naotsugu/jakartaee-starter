package com.mammb.jakartaee.starter.domail.example.book;

import com.mammb.jakartaee.starter.domail.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Arrays;
import java.util.List;

@Entity(name = Book.NAME)
public class Book extends BaseEntity<Book> {

    public static final String NAME = "BOOKS";

    @NotBlank
    @Size(max = 200)
    @Column(length = 200, nullable = false)
    private String title;

    private String summary;

    @Size(max = 13)
    @Column(length = 13)
    private String isbn;

    @ManyToMany
    private List<Author> authors;


    protected Book() {
    }

    protected Book(String title, String summary, String isbn, List<Author> authors) {
        this.title = title;
        this.summary = summary;
        this.isbn = isbn;
        this.authors = authors;
    }

    public static Book of(String title, String summary, String isbn, Author... authors) {
        return new Book(title, summary, isbn, Arrays.asList(authors));
    }

    public String getTitle() {
        return title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getSummary() {
        return summary;
    }

    public String getIsbn() {
        return isbn;
    }
}
