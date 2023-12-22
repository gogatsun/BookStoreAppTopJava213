package com.example.bookstoreapplication.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "client_t")
public class Client {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "client")
    private Set<Order> orderSet;

    public Client() {
        id = null;
        user = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Order> getOrderSet() {
        return orderSet;
    }

    public void setOrderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
    }
}
