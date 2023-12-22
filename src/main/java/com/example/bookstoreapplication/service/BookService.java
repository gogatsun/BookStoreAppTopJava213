package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.entity.Book;
import com.example.bookstoreapplication.entity.BookAuthor;
import com.example.bookstoreapplication.entity.BookGenre;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**Методы:<br>
 * <i>add()</i> - добавить книгу<br>
 * <i>update()</i> - редактировать книгу<br>
 * <i>findById()</i> - получить по id<br>
 * <i>findAll()</i> - получить все<br>
 * <i>delete()</i> - удалить по id<br>*/
@Service
public interface BookService {

    // добавить
    Optional<Book> add(Book book);

    // редактировать
    Optional<Book> update(Book book);

    // получить по id
    Optional<Book> findById(Long id);

    // получить все
    List<Book> findAll();

    // получить 4 книги
    List<Book> findList(int count);

    // получить ISBN
    Optional<Book> findByIsbn(String isbn);

    // удалить
    Optional<Book> delete(Long id);

    // добавление автора
    boolean addAuthor(BookAuthor bookAuthor);

    //добавление жанра
    boolean addGenre(BookGenre bookGenre);
}
