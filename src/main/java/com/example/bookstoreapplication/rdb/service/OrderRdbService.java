package com.example.bookstoreapplication.rdb.service;

import com.example.bookstoreapplication.entity.Book;
import com.example.bookstoreapplication.entity.Client;
import com.example.bookstoreapplication.entity.Order;
import com.example.bookstoreapplication.entity.OrderBook;
import com.example.bookstoreapplication.rdb.repositories.ClientRepository;
import com.example.bookstoreapplication.rdb.repositories.OrderBookRepository;
import com.example.bookstoreapplication.rdb.repositories.OrderRepository;
import com.example.bookstoreapplication.service.BookService;
import com.example.bookstoreapplication.service.OrderService;
import com.sun.source.tree.LambdaExpressionTree;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderRdbService implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final BookService bookService;

    private final OrderBookRepository orderBookRepository;

    public OrderRdbService(OrderRepository orderRepository,
                           ClientRepository clientRepository,
                           BookService bookService,
                           OrderBookRepository orderBookRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.bookService = bookService;
        this.orderBookRepository = orderBookRepository;
    }

    @Override
    public boolean buyBook(Long bookId, Long userId) {
        Optional<Client> client = clientRepository.findById(userId);
        if (client.isEmpty()) {
            return false;
        }
        Optional<Book> book = bookService.findById(bookId);
        if (book.isEmpty()) {
            return false;
        }

        Optional<Order> notCompletedOrder = findByNotCompleted(userId);
        if (notCompletedOrder.isPresent()) {
            Optional<OrderBook> orderBook =
                    findOrderProductByOrder_IdAndBook_Id(notCompletedOrder.get().getId(), bookId);
            if (orderBook.isPresent()) {
                orderBook.get().setQuantity(orderBook.get().getQuantity() + 1);
                orderBookRepository.save(orderBook.get());
            } else {
                OrderBook newOrderBook = new OrderBook();
                newOrderBook.setQuantity(1);
                newOrderBook.setOrder(notCompletedOrder.get());
                newOrderBook.setBook(book.get());
                orderBookRepository.save(newOrderBook);
            }

        } else {
            Order newOrder = new Order();
            newOrder.setClient(client.get());
            newOrder = orderRepository.save(newOrder);
            OrderBook orderBook = new OrderBook();
            orderBook.setQuantity(1);
            orderBook.setOrder(newOrder);
            orderBook.setBook(book.get());
            orderBookRepository.save(orderBook);
        }
        return true;
    }

    @Override
    public boolean deleteItem(Long orderBookId) {
        Optional<OrderBook> orderBook = orderBookRepository.findById(orderBookId);
        if (orderBook.isPresent()) {
            orderBook.get().setQuantity(orderBook.get().getQuantity() - 1);
            if (orderBook.get().getQuantity() == 0) {
                orderBookRepository.deleteById(orderBookId);
            } else {
                orderBookRepository.save(orderBook.get());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addItem(Long orderBookId) {
        Optional<OrderBook> orderBook = orderBookRepository.findById(orderBookId);
        if (orderBook.isPresent()) {
            orderBook.get().setQuantity(orderBook.get().getQuantity() + 1);
            orderBookRepository.save(orderBook.get());
            return true;
        }
        return false;
    }

    @Override
    public Optional<Order> findByNotCompleted(Long clientId) {
        Optional<Order> order = orderRepository.findFirstByClient_IdAndCompletedIsFalse(clientId);
        if (order.isPresent()) {
            Set<OrderBook> orderBookSet = order.get().getOrderBookSet();
            order.get()
                    .setOrderBookSet(orderBookSet.stream()
                    .sorted(Comparator.comparing(op -> op.getBook().getTitle()))
                    .collect(Collectors.toCollection(LinkedHashSet::new)));
            return order;
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Order> findAllCompletedOrders(Long clientId) {
        return orderRepository.findByClient_IdAndCompletedIsTrue(clientId);
    }

    @Override
    public Optional<OrderBook> findOrderProductByOrder_IdAndBook_Id(Long orderId, Long bookId) {
        Iterable<OrderBook> orderBooks = orderBookRepository.findAll();
        for (OrderBook orderBook : orderBooks) {
            if (orderBook.getOrder().getId().equals(orderId)
                    && orderBook.getBook().getId().equals(bookId)) {
                return Optional.of(orderBook);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean completeOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty() || order.get().getCompleted()) {
            return false;
        }
        for (OrderBook orderBook : order.get().getOrderBookSet()) {
            Optional<Book> book = bookService.findById(orderBook.getBook().getId());
            if (book.isPresent()) {
                int quantity = book.get().getQuantity() - orderBook.getQuantity();
                if (quantity < 0) {
                    return false;
                }
            } else {
                return false;
            }
        }
        for (OrderBook orderBook : order.get().getOrderBookSet()) {
            Book book = orderBook.getBook();
            book.setQuantity(book.getQuantity() - orderBook.getQuantity());
            bookService.update(book);
        }
        order.get().setCompleted(true);
        order.get().setCreatedAt(new Date());
        orderRepository.save(order.get());
        return true;
    }

}
