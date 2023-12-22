package com.example.bookstoreapplication.rdb.service;

import com.example.bookstoreapplication.entity.BookAuthor;
import com.example.bookstoreapplication.rdb.repositories.BookAuthorRepository;
import com.example.bookstoreapplication.service.BookAuthorService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookAuthorRdbService implements BookAuthorService {
    private final BookAuthorRepository bookAuthorRepository;

    public BookAuthorRdbService(BookAuthorRepository bookAuthorRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
    }

    @Override
    public Optional<BookAuthor> deleteById(Integer id) {
        Optional<BookAuthor> deleted = bookAuthorRepository.findById(id);
        if (deleted.isPresent()) {
            bookAuthorRepository.deleteById(id);
        }
        return deleted;
    }
}
