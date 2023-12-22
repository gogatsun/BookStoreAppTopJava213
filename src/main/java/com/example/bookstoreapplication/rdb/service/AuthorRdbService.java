package com.example.bookstoreapplication.rdb.service;

import com.example.bookstoreapplication.entity.Author;
import com.example.bookstoreapplication.entity.Book;
import com.example.bookstoreapplication.rdb.repositories.AuthorRepository;
import com.example.bookstoreapplication.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class AuthorRdbService implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorRdbService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Optional<Author> add(Author author) {
        List<Author> existing = authorRepository.findBySurnameAndNameAndPatronymic(author.getSurname(),
                author.getName(), author.getPatronymic());
        // если автора с идентичными ФИО нет
        if (existing.isEmpty()) {
            return Optional.of(authorRepository.save(author));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = (List<Author>) authorRepository.findAll();
        return authors.stream().sorted(Comparator.comparing(Author::getSurname)).toList();
    }

    @Override
    public Optional<Author> update(Author author) {
        List<Author> updated = authorRepository.findBySurnameAndNameAndPatronymic(author.getSurname(),
                author.getName(), author.getPatronymic());
        if (updated.isEmpty()) {
            return Optional.of(authorRepository.save(author));
        }
        return Optional.empty();
    }

    @Override
    public List<Author> findList(int count) {
        List<Author> authorList = (List<Author>) authorRepository.findAll();
        Random random = new Random();
        List<Integer> ints = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(0, authorList.size());
            if (!ints.contains(index)) {
                authors.add(i, authorList.get(index));
                ints.add(index);
            } else {
                i--;
            }
        }
        return authors;
    }


    @Override
    public List<Author> findByName(String name) {
        List<Author> bySurname = authorRepository.findBySurname(name);
        List<Author> byName = authorRepository.findByName(name);
        return Stream.concat(bySurname.stream(), byName.stream()).toList();
    }
}
