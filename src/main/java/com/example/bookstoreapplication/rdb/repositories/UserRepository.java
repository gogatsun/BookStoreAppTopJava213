package com.example.bookstoreapplication.rdb.repositories;

import com.example.bookstoreapplication.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByLogin(String login);
}
