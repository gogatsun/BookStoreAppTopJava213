package com.example.bookstoreapplication.rdb.repositories;

import com.example.bookstoreapplication.entity.AgeLimit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgeLimitRepository extends CrudRepository<AgeLimit, Long> {
}
