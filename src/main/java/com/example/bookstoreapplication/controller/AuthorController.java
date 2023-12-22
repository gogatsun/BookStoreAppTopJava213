package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.entity.Author;
import com.example.bookstoreapplication.entity.Book;
import com.example.bookstoreapplication.entity.BookAuthor;
import com.example.bookstoreapplication.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("")
    public String findAll(Model model) {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "authors/authors";
    }

    @GetMapping("new-author")
    public String getAddForm(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "authors/add-author-form";
    }

    @PostMapping("new-author")
    public String postAddForm(Author author, RedirectAttributes redirectAttributes) {
        Optional<Author> newAuthor = authorService.add(author);
        if (newAuthor.isPresent()) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Автор успешно добавлен!");
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так...");
        }

        return "redirect:/authors";
    }

    @GetMapping("update-author/{id}")
    public String getUpdateForm(@PathVariable Long id, Model model,  RedirectAttributes redirectAttributes) {
        Optional<Author> updated = authorService.findById(id);
        if (updated.isPresent()) {
            model.addAttribute("author", updated.get());
            return "authors/update-author-form";
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так...");
        }
        return "redirect:/authors";
    }

    @PostMapping("update-author")
    public String postUpdateForm(Author author, RedirectAttributes redirectAttributes) {
        Optional<Author> updated = authorService.update(author);
        if (updated.isPresent()) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Изменения сохранены");
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так...");
        }
        return "redirect:/authors";
    }

    @GetMapping("author-books/{id}")
    public String getAuthorBooks(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Author> author = authorService.findById(id);
        if (author.isEmpty()) {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так...");
            return "redirect:/authors";
        } else {
            Set<BookAuthor> bookAuthorSet = author.get().getBookAuthorSet();
            List<Book> books = new ArrayList<>();
            for (BookAuthor bookAuthor: bookAuthorSet) {
                books.add(bookAuthor.getBook());
            }
            model.addAttribute("author", author.get());
            model.addAttribute("books", books);
            return "authors/author-books";
        }

    }
}
