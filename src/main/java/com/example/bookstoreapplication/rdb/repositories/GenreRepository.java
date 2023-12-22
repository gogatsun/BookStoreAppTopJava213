package com.example.bookstoreapplication.rdb.repositories;

import com.example.bookstoreapplication.entity.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {

}
