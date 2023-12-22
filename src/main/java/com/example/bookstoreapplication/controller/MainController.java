package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.entity.Author;
import com.example.bookstoreapplication.entity.Book;
import com.example.bookstoreapplication.entity.Series;
import com.example.bookstoreapplication.service.AuthorService;
import com.example.bookstoreapplication.service.BookService;
import com.example.bookstoreapplication.service.SeriesService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final SeriesService seriesService;

    public MainController(BookService bookService, AuthorService authorService, SeriesService seriesService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.seriesService = seriesService;
    }

    @GetMapping("")
    public String index(Model model) {
        int COUNT = 4;
        List<Book> books = bookService.findList(COUNT);
        model.addAttribute("books", books);
        List<Author> authors = authorService.findList(COUNT);
        model.addAttribute("authors", authors);
        List<Series> series = seriesService.findList(COUNT - 2);
        model.addAttribute("series", series);
        return "index";
    }



}
