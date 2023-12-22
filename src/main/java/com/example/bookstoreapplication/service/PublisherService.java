package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.entity.Publisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**Методы:<br>
 * <i>findAll()</i> - получить все издательства<br>
 * <i>findById()</i> - получить по ID<br>
 * <i>add()</i> - добавление<br>
 * <i>update()</i>- изменение
 * */

@Service
public interface PublisherService {

    // получить все издательства
    List<Publisher> findAll();

    // получить по ID
    Optional<Publisher> findById(Long id);

    //добавление издательства
    Optional<Publisher> add(Publisher publisher);

    // изменение издательства
    Optional<Publisher> update(Publisher publisher);

    // получить COUNT записей
    List<Publisher> findList(int count);

}
