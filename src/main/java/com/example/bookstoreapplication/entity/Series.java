package com.example.bookstoreapplication.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "series_t")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID книжной серии

    @Column(name = "title_f", length = 150, unique = true, nullable = false)
    private String title; // название серии книг

    @Column(name = "description_f", columnDefinition = "LONGTEXT", nullable = false)
    private String description;

    @OneToMany(mappedBy = "series")
    private Set<Book> book;

    public Series() {
        id = null;
        title = "";
        description = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Book> getBook() {
        return book;
    }

    public void setBook(Set<Book> book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return title;
    }
}
