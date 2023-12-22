package com.example.bookstoreapplication.rdb.repositories;

import com.example.bookstoreapplication.entity.BookGenre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookGenreRepository extends CrudRepository<BookGenre, Long> {

}
