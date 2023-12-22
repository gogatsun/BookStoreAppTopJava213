package com.example.bookstoreapplication.rdb.repositories;

import com.example.bookstoreapplication.entity.CoverType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverTypeRepository extends CrudRepository<CoverType, Long> {
}
