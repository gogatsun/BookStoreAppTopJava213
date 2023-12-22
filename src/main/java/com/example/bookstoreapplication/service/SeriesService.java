package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.entity.Series;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**Методы:<br>
 * <i>add()</i> - добавление<br>
 * <i>update()</i> - изменение<br>
 * <i>findById()</i> - получить по id<br>
 * <i>findAll()</i> - получить все<br>
 * <i>delete()</i> - удаление(?)*/

@Service
public interface SeriesService {

    // добавление книжной серии
    Optional<Series> add(Series series);

    // изменение книжной серии
    Optional<Series> update(Series series);

    //получить по id
    Optional<Series> findById(Long id);

    //получить все
    List<Series> findAll();

    // получить COUNT записей
    List<Series> findList(int count);

    //удаление серии(?)
    //Optional<Series> delete(Long id);

}
