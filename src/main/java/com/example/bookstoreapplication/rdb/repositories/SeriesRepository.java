package com.example.bookstoreapplication.rdb.repositories;

import com.example.bookstoreapplication.entity.Series;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeriesRepository extends CrudRepository<Series, Long> {
    Optional<Series> findByTitle(String title);
}
