package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.entity.Book;
import com.example.bookstoreapplication.entity.Client;
import com.example.bookstoreapplication.entity.Order;
import com.example.bookstoreapplication.entity.User;
import com.example.bookstoreapplication.rdb.repositories.ClientRepository;
import com.example.bookstoreapplication.service.BookService;
import com.example.bookstoreapplication.service.OrderService;
import com.example.bookstoreapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ClientRepository clientRepository;
    private final BookService bookService;

    public OrderController(OrderService orderService, UserService userService,
                           ClientRepository clientRepository, BookService bookService) {
        this.orderService = orderService;
        this.userService = userService;
        this.clientRepository = clientRepository;
        this.bookService = bookService;
    }

    @GetMapping("")
    public String orderDetails(Model model, Principal principal) {
        Optional<User> user = userService.findByLogin(principal.getName());
        if (user.isEmpty()) {
            return "redirect:/error";
        }
        Optional<Client> client = clientRepository.findById(user.get().getId());
        if (client.isEmpty()) {
            return "redirect:/error";
        }
        Iterable<Order> completedOrders = orderService.findAllCompletedOrders(client.get().getUser().getId());
        model.addAttribute("completedOrders", completedOrders);
        Optional<Order> notCompletedOrder = orderService.findByNotCompleted(client.get().getUser().getId());
        if (notCompletedOrder.isPresent()){
            model.addAttribute("notCompletedOrder", notCompletedOrder.orElse(null));
            if (notCompletedOrder.get().getOrderBookSet().isEmpty()) {
                model.addAttribute("orderProductNull", true);
            } else {
                model.addAttribute("orderProductNull", false);
            }
        } else {
            model.addAttribute("orderProductNull", true);
        }
        notCompletedOrder.ifPresent(order -> model.addAttribute("totalPrice", order.totalPrice()));
        return "order/order-details";
    }

    @GetMapping("complete/{orderId}")
    public String complete(@PathVariable Long orderId, RedirectAttributes redirectAttributes) {
        if (orderService.completeOrder(orderId)) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Заказ успешно оформлен");
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так... " +
                            "Проверьте количество доступных для заказа книг на складе");
        }
        return "redirect:/order";
    }

    @GetMapping("buy/{bookId}")
    public String buy(@PathVariable Long bookId, Principal principal,
                      RedirectAttributes redirectAttributes) {
        Optional<User> user = userService.findByLogin(principal.getName());
        if (user.isPresent()) {
            Optional<Client> client = clientRepository.findById(user.get().getId());
            if (client.isPresent()) {
                Optional<Book> book = bookService.findById(bookId);
                if (book.isPresent()) {
                    if (book.get().getQuantity() > 0) {
                        if (!orderService.buyBook(bookId, client.get().getUser().getId())) {
                            redirectAttributes.addFlashAttribute("dangerMessage",
                                    "Что-то пошло не так... " +
                                            "Проверьте количество доступных для заказа книг на складе");
                        }
                    }
                }
            }
        }
        return "redirect:/books";
    }

    @GetMapping("delete-item/{orderBookId}")
    private String deleteItem(@PathVariable Long orderBookId, RedirectAttributes redirectAttributes) {
        if (!orderService.deleteItem(orderBookId)) {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Не удалось уменьшить или удалить товар.");
        }
        return "redirect:/order";
    }

    @GetMapping("add-item/{orderBookId}")
    private String addItem(@PathVariable Long orderBookId, RedirectAttributes redirectAttributes) {
        if (!orderService.addItem(orderBookId)) {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Не удалось добавить товар.");
        }
        return "redirect:/order";
    }
}
