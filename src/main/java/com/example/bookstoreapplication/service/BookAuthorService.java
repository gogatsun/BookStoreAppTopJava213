package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.entity.BookAuthor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BookAuthorService {
    Optional<BookAuthor> deleteById(Integer id);
}
