package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.entity.BookGenre;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BookGenreService {

    // удалить по id
    Optional<BookGenre> deleteById(Long id);
}
