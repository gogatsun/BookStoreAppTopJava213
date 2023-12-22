package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.entity.Order;
import com.example.bookstoreapplication.entity.OrderBook;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface OrderService {

    boolean buyBook(Long bookId, Long userId);

    boolean deleteItem(Long orderBookId);
    boolean addItem(Long orderBookId);

    Optional<Order> findByNotCompleted(Long clientId);

    Iterable<Order> findAllCompletedOrders(Long clientId);

    Optional<OrderBook> findOrderProductByOrder_IdAndBook_Id(Long orderId, Long bookId);

    boolean completeOrder(Long orderId);

}
