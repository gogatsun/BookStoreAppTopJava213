package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.entity.Author;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**Методы:<br>
 * <i>add()</i> - добавить автора<br>
 * <i>findById</i> - получить по ID<br>
 * <i>findAll</i> - получить все<br>
 * <i>update</i> - изменить<br>
 * <i>findByName</i> - получить по имени*/
@Service
public interface AuthorService {

    // добавить
    Optional<Author> add(Author author);

    // получить по ID
    Optional<Author> findById(Long id);

    // получить все
    List<Author> findAll();

    //изменить
    Optional<Author> update(Author author);

    // получить COUNT записей
    List<Author> findList(int count);

    //получить по имени (TODO: додумать для реализации поиска)
    List<Author> findByName(String name);

}
