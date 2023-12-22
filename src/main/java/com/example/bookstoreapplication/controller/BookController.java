package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.entity.*;
import com.example.bookstoreapplication.rdb.service.BookAuthorRdbService;
import com.example.bookstoreapplication.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final SeriesService seriesService;
    private final CoverTypeService coverTypeService;
    private final AgeLimitService ageLimitService;
    private final BookAuthorService bookAuthorService;
    private final BookGenreService bookGenreService;
    private final GenreService genreService;

    public BookController(BookService bookService,
                          AuthorService authorService,
                          PublisherService publisherService,
                          SeriesService seriesService,
                          CoverTypeService coverTypeService,
                          AgeLimitService ageLimitService,
                          BookAuthorRdbService bookAuthorService,
                          BookGenreService bookGenreService,
                          GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
        this.seriesService = seriesService;
        this.coverTypeService = coverTypeService;
        this.ageLimitService = ageLimitService;
        this.bookAuthorService = bookAuthorService;
        this.bookGenreService = bookGenreService;
        this.genreService = genreService;
    }

    @GetMapping("")
    public String findAll(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books/books";
    }

    @GetMapping("new-book")
    public String getAddForm(Model model) {
        Book book = new Book();
        model.addAttribute("NewBook", book);
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        List<Publisher> publishers = publisherService.findAll();
        model.addAttribute("publishers", publishers);
        List<Series> series = seriesService.findAll();
        model.addAttribute("series", series);
        List<CoverType> coverTypes = coverTypeService.findAll();
        model.addAttribute("coverTypes", coverTypes);
        List<AgeLimit> ageLimits = ageLimitService.findAll();
        model.addAttribute("ageLimits", ageLimits);
        model.addAttribute("bookAuthors", new BookAuthor());
        return "/books/add-book-form";
    }

    @PostMapping("new-book")
    public String postAddForm(@RequestParam MultipartFile bookImage, Book book,
                              RedirectAttributes redirectAttributes) throws IOException {
        if (bookImage.isEmpty()) {
            book.setBookImageData(null);
        } else {
            String bookImageData = Base64.getEncoder().encodeToString(bookImage.getBytes());
            book.setBookImageData(bookImageData);
        }
        Optional<Book> existing = bookService.add(book);
        if (existing.isPresent()) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Книга успешно добавлена");
            return "redirect:/books/" + existing.get().getId();
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так...");
            return "redirect:/books";
        }
    }

    @GetMapping("{id}")
    public String getAddAuthor(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes){
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            model.addAttribute("updateBook", book.get());
            model.addAttribute("bookAuthors", new BookAuthor());
            model.addAttribute("bookGenres", new BookGenre());
            List<Author> authors = new ArrayList<>();
            for (Author author : authorService.findAll()) {
                if (book.get().getBookAuthorSet()
                        .stream()
                        .noneMatch(c -> Objects.equals(c.getAuthor().getId(), author.getId()))) {
                    authors.add(author);
                }
            }
            model.addAttribute("authors", authors);

            List<Genre> genres = new ArrayList<>();
            for (Genre genre : genreService.findAll()) {
                if (book.get().getBookGenreSet()
                        .stream().noneMatch(c -> Objects.equals(c.getGenre().getId(), genre.getId()))) {
                    genres.add(genre);
                }
            }
            model.addAttribute("genres", genres);
            return "books/add-authors";
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так...");
            return "redirect:/books";
        }
    }

    @PostMapping("{id}/add-authors")
    public String postAddAuthor(@PathVariable Long id, BookAuthor bookAuthor, RedirectAttributes redirectAttributes) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            bookAuthor.setBook(book.get());
            if (bookService.addAuthor(bookAuthor)) {
                redirectAttributes.addFlashAttribute("successMessage",
                        "Автор успешно добавлен!");
            } else {
                redirectAttributes.addFlashAttribute("dangerMessage",
                        "Что-то пошло не так...");
            }
            return "redirect:/books/" + book.get().getId();
        } else {
            return "redirect:/books"; // книга не найдена
        }
    }

    @GetMapping("{idBook}/delete-author/{id}")
    public String postDeleteAuthor(@PathVariable Integer id, @PathVariable Long idBook,
                                   RedirectAttributes redirectAttributes) {
        Optional<BookAuthor> deleteAuthor = bookAuthorService.deleteById(id);
        if (deleteAuthor.isPresent()) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Автор удалён");
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Не удалось выполнить удаление");
        }
        return "redirect:/books/" + idBook;
    }

    @PostMapping("{id}/add-genres")
    public String postAddGenre(@PathVariable Long id, BookGenre bookGenre, RedirectAttributes redirectAttributes) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            bookGenre.setBook(book.get());
            if (bookService.addGenre(bookGenre)) {
                redirectAttributes.addFlashAttribute("successMessage",
                        "Жанр успешно добавлен");
            } else {
                redirectAttributes.addFlashAttribute("dangerMessage",
                        "Что-то пошло не так...");
            }
            return "redirect:/books/" + book.get().getId();
        } else {
            return "redirect:/books";
        }
    }

    @GetMapping("{idBook}/delete-genre/{id}")
    public String postDeleteGenre(@PathVariable Long id, @PathVariable Long idBook,
                                   RedirectAttributes redirectAttributes) {
        Optional<BookGenre> deleteGenre = bookGenreService.deleteById(id);
        if (deleteGenre.isPresent()) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Жанр удалён");
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Не удалось выполнить удаление");
        }
        return "redirect:/books/" + idBook;
    }

    @GetMapping("update-book/{id}")
    public String getUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            List<Author> authors = authorService.findAll();
            model.addAttribute("authors", authors);
            List<Publisher> publishers = publisherService.findAll();
            model.addAttribute("publishers", publishers);
            List<Series> series = seriesService.findAll();
            model.addAttribute("series", series);
            List<CoverType> coverTypes = coverTypeService.findAll();
            model.addAttribute("coverTypes", coverTypes);
            List<AgeLimit> ageLimits = ageLimitService.findAll();
            model.addAttribute("ageLimits", ageLimits);
            model.addAttribute("updateBook", book.get());
            model.addAttribute("bookAuthors", new BookAuthor());
            return "/books/update-book-form";
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так...");
            return "redirect:/books";
        }
    }

    @PostMapping("update-book")
    public String postUpdateForm(@RequestParam MultipartFile bookImage, Book book,
                                 RedirectAttributes redirectAttributes) throws IOException {
        Optional<Book> book1 = bookService.findById(book.getId());
        if (book1.isPresent()) {
            if (bookImage.isEmpty()) {
                book.setBookImageData(book1.get().getBookImageData());
            } else {
                String bookImageData = Base64.getEncoder().encodeToString(bookImage.getBytes());
                book.setBookImageData(bookImageData);
            }
        }
        Optional<Book> updated = bookService.update(book);
        if (updated.isPresent()) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Изменения сохранены!");
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так...");
        }
        return "redirect:/books";
    }

    @GetMapping("book-details/{id}")
    public String getBookDetails(Model model, RedirectAttributes redirectAttributes, @PathVariable Long id) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "/books/book-details";
        } else {
            redirectAttributes.addFlashAttribute("dangerMessage",
                    "Что-то пошло не так...");
            return "redirect:/books";
        }
    }

}
