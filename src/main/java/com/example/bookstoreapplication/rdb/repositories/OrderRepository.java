package com.example.bookstoreapplication.rdb.repositories;

import com.example.bookstoreapplication.entity.Client;
import com.example.bookstoreapplication.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    Optional<Order> findFirstByClient_IdAndCompletedIsFalse (Long clientId);
    Iterable<Order> findByClient_IdAndCompletedIsTrue (Long clientId);

}