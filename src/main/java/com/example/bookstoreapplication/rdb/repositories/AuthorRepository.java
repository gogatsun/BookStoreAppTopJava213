package com.example.bookstoreapplication.rdb.repositories;

import com.example.bookstoreapplication.entity.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    List<Author> findBySurname(String surname);
    List<Author> findByName(String name);
    List<Author> findBySurnameAndNameAndPatronymic(String surname, String name, String patronymic);
}
