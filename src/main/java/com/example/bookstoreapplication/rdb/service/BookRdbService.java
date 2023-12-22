package com.example.bookstoreapplication.rdb.service;

import com.example.bookstoreapplication.entity.*;
import com.example.bookstoreapplication.rdb.repositories.BookAuthorRepository;
import com.example.bookstoreapplication.rdb.repositories.BookGenreRepository;
import com.example.bookstoreapplication.rdb.repositories.BookRepository;
import com.example.bookstoreapplication.rdb.repositories.GenreRepository;
import com.example.bookstoreapplication.service.AuthorService;
import com.example.bookstoreapplication.service.BookService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookRdbService implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookAuthorRepository bookAuthorRepository;
    private final GenreRepository genreRepository;
    private final BookGenreRepository bookGenreRepository;

    public BookRdbService(BookRepository bookRepository, AuthorService authorService, BookAuthorRepository bookAuthorRepository, GenreRepository genreRepository, BookGenreRepository bookGenreRepository) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.bookAuthorRepository = bookAuthorRepository;
        this.genreRepository = genreRepository;
        this.bookGenreRepository = bookGenreRepository;
    }


    @Override
    public Optional<Book> add(Book book) {
        Optional<Book> bookIsbn = bookRepository.findByIsbn(book.getIsbn());
        if (bookIsbn.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(bookRepository.save(book));
    }

    @Override
    public Optional<Book> update(Book book) {
        Optional<Book> updated = bookRepository.findById(book.getId());
        if (updated.isEmpty()) {
            return Optional.empty();
        }
        Optional<Book> bookIsbn = bookRepository.findByIsbn(book.getIsbn());
        if (bookIsbn.isPresent() && !Objects.equals(updated.get().getId(), bookIsbn.get().getId())) {
            return Optional.empty();
        }
        return Optional.of(bookRepository.save(book));
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    @Override
    public List<Book> findList(int count) {
        List<Book> bookList = (List<Book>) bookRepository.findAll();
        Random random = new Random();
        List<Book> books = new ArrayList<>();
        List<Integer> ints = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(0, bookList.size());
            if (!ints.contains(index)) {
                books.add(i, bookList.get(index));
                ints.add(index);
            } else {
                i--;
            }
        }
        return books;
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Optional<Book> delete(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean addAuthor(BookAuthor bookAuthor) {
        Optional<Book> book = findById(bookAuthor.getBook().getId());
        if (book.isEmpty()) {
            return false;
        }
        Optional<Author> author = authorService.findById(bookAuthor.getAuthor().getId());
        if (author.isEmpty()) {
            return false;
        }
        Long newBookAuthorId = bookAuthor.getAuthor().getId();
        Set<BookAuthor> bookAuthors = book.get().getBookAuthorSet();
        for (BookAuthor bA : bookAuthors) {
            if (Objects.equals(bA.getAuthor().getId(),newBookAuthorId)) {
                return false;
            }
        }
        bookAuthorRepository.save(bookAuthor);
        return true;
    }

    @Override
    public boolean addGenre(BookGenre bookGenre) {
        Optional<Book> book = findById(bookGenre.getBook().getId());
        if (book.isEmpty()) {
            return false;
        }
        Optional<Genre> genre = genreRepository.findById(bookGenre.getGenre().getId());
        if (genre.isEmpty()) {
            return false;
        }
        Long newBookGenre = bookGenre.getGenre().getId();
        Set<BookGenre> bookGenres = book.get().getBookGenreSet();
        for (BookGenre bG : bookGenres) {
            if (Objects.equals(bG.getGenre().getId(), newBookGenre)) {
                return false;
            }
        }
        bookGenreRepository.save(bookGenre);
        return true;
    }
}
