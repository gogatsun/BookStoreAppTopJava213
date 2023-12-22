package com.example.bookstoreapplication.rdb.repositories;

import com.example.bookstoreapplication.entity.BookAuthor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookAuthorRepository extends CrudRepository<BookAuthor, Integer> {

}
