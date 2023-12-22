package com.example.bookstoreapplication.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "cover_type_t")
public class CoverType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID типа обложки

    @Column(name = "title_f", length = 150, unique = true, nullable = false)
    private String title; // название

    @Column(name = "description_f")
    private String description;  // описание (необязательно для заполнения)

    @OneToMany(mappedBy = "coverType")
    private Set<Book> book;

    public CoverType() {
        id = null;
        title = "Default constructor";
        description = "Default constructor";
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
