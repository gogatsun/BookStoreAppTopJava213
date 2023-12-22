package com.example.bookstoreapplication.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "genre_t")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title_f", nullable = false, unique = true)
    private String title;

    @Column(name = "description_f")
    private String description;

    @OneToMany(mappedBy = "genre")
    private Set<BookGenre> bookGenreSet;

    public Genre() {
        id = null;
        title = null;
        description = null;
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

    public Set<BookGenre> getBookGenreSet() {
        return bookGenreSet;
    }

    public void setBookGenreSet(Set<BookGenre> bookGenreSet) {
        this.bookGenreSet = bookGenreSet;
    }
}
