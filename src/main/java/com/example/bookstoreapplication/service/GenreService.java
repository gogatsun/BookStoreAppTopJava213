package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.entity.Genre;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface GenreService {

    // получить по ID
    Optional<Genre> findById(Long id);

    // получить все записи
    List<Genre> findAll();
}
