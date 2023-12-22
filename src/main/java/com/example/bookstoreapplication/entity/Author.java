package com.example.bookstoreapplication.entity;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "author_t")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID автора

    @Column(name = "surname_f", nullable = false)
    private String surname; // фамилия

    @Column(name = "name_f", nullable = false)
    private String name; // имя

    @Column(name = "patronymic_f", nullable = false)
    private String patronymic; // отчество

    @OneToMany(mappedBy = "author")
    private Set<BookAuthor> bookAuthorSet;

    public String snp() {
        if (patronymic.isEmpty()) {
            return String.format("%s. %s", name.substring(0, 1).toUpperCase(), surname);
        } else {
            return String.format("%s. %s. %s", name.substring(0, 1).toUpperCase(),
                    patronymic.substring(0, 1).toUpperCase(), surname);
        }
    }

    public Author() {
        surname = "";
        name = "";
        patronymic = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Set<BookAuthor> getBookAuthorSet() {
        return bookAuthorSet;
    }

    public void setBookAuthorSet(Set<BookAuthor> bookAuthorSet) {
        this.bookAuthorSet = bookAuthorSet;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", bookAuthorSet=" + bookAuthorSet +
                '}';
    }
}
