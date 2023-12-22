package com.example.bookstoreapplication.rdb.service;

import com.example.bookstoreapplication.entity.Genre;
import com.example.bookstoreapplication.entity.Publisher;
import com.example.bookstoreapplication.rdb.repositories.GenreRepository;
import com.example.bookstoreapplication.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreRdbService implements GenreService {

    private final GenreRepository genreRepository;

    public GenreRdbService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public List<Genre> findAll() {
        List<Genre> genres = (List<Genre>) genreRepository.findAll();
        return genres.stream().sorted(Comparator.comparing(Genre::getTitle)).collect(Collectors.toList());
    }
}
