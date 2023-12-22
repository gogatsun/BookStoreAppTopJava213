package com.example.bookstoreapplication.rdb.repositories;

import com.example.bookstoreapplication.entity.OrderBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBookRepository extends CrudRepository<OrderBook, Long> {
}
