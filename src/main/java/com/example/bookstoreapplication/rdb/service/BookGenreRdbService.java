package com.example.bookstoreapplication.rdb.service;

import com.example.bookstoreapplication.entity.BookGenre;
import com.example.bookstoreapplication.rdb.repositories.BookGenreRepository;
import com.example.bookstoreapplication.service.BookGenreService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookGenreRdbService implements BookGenreService {

    private final BookGenreRepository bookGenreRepository;

    public BookGenreRdbService(BookGenreRepository bookGenreRepository) {
        this.bookGenreRepository = bookGenreRepository;
    }

    @Override
    public Optional<BookGenre> deleteById(Long id) {
        Optional<BookGenre> deleted = bookGenreRepository.findById(id);
        if (deleted.isPresent()) {
            bookGenreRepository.deleteById(id);
        }
        return deleted;
    }
}
