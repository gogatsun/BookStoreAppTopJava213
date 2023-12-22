package com.example.bookstoreapplication.rdb.repositories;

import com.example.bookstoreapplication.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
}
