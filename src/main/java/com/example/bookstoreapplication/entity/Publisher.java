package com.example.bookstoreapplication.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "publisher_t")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID издательства

    @Column(name = "title_f", length = 150, unique = true, nullable = false)
    private String title; // название издательства

    @Column(name = "description_f")
    private String description; // описание (необязательно для заполнения)

    @OneToMany(mappedBy = "publisher")
    private Set<Book> book;

    public Publisher() {
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
        return id + " " + title;
    }
}
