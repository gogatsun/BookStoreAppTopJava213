package com.example.bookstoreapplication.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_t")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_f", unique = true, nullable = false)
    private String login;

    @Column(name = "password_f", nullable = false)
    private String password;

    @Column(name = "role_f", nullable = false)
    private String role;

    public User() {
        id = null;
        login = "";
        password = "";
        role = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
