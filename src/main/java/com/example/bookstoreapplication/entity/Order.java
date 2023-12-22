package com.example.bookstoreapplication.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "order_t")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at_f")
    private Date createdAt;

    @Column(name = "completed_f", nullable = false)
    private Boolean completed;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "order")
    private Set<OrderBook> orderBookSet;

    public Order() {
        id = null;
        createdAt = null;
        completed = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<OrderBook> getOrderBookSet() {
        return orderBookSet;
    }

    public void setOrderBookSet(Set<OrderBook> orderBookSet) {
        this.orderBookSet = orderBookSet;
    }

    public double totalPrice() {
        double total = 0.0;
        if (!orderBookSet.isEmpty()) {
            for (OrderBook orderBook : orderBookSet) {
                total += orderBook.getBook().getPrice() * orderBook.getQuantity();
            }
        }
        return total;
    }
}
