package com.example.bookstoreapplication.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "age_limit_t")
public class AgeLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID возрастного ограничения

    @Column(name = "title_f", length = 9, unique = true, nullable = false)
    private String title; // название ('0+, '6+', '12+', '16+, '18+' и 'Не задано')

    @Column(name = "description_f")
    private String description; // описание (необязательно для заполнения)

    @OneToMany(mappedBy = "ageLimit")
    private Set<Book> book;

    public AgeLimit() {
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
