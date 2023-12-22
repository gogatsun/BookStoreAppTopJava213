package com.example.bookstoreapplication.rdb.repositories;

import com.example.bookstoreapplication.entity.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends CrudRepository<Publisher, Long> {
    Optional<Publisher> findByTitle(String title);
}
