package com.example.bookstoreapplication.rdb.repositories;

import com.example.bookstoreapplication.entity.Book;
import com.example.bookstoreapplication.entity.BookAuthor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

}
