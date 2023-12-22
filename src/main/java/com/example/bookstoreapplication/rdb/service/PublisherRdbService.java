package com.example.bookstoreapplication.rdb.service;

import com.example.bookstoreapplication.entity.Book;
import com.example.bookstoreapplication.entity.Publisher;
import com.example.bookstoreapplication.rdb.repositories.PublisherRepository;
import com.example.bookstoreapplication.service.PublisherService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PublisherRdbService implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherRdbService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> findAll() {
        List<Publisher> publishers = (List<Publisher>) publisherRepository.findAll();
        return publishers.stream().sorted(Comparator.comparing(Publisher::getTitle)).toList();
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        return publisherRepository.findById(id);
    }

    // добавление издательства
    @Override
    public Optional<Publisher> add(Publisher publisher) {
        Optional<Publisher> existing = publisherRepository.findByTitle(publisher.getTitle());
        if (existing.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(publisherRepository.save(publisher));
    }

    @Override
    public Optional<Publisher> update(Publisher publisher) {
        Optional<Publisher> updated = publisherRepository.findById(publisher.getId());
        if (updated.isEmpty()) {
            return Optional.empty();
        }
        Optional<Publisher> existing = publisherRepository.findByTitle(publisher.getTitle());
        if (existing.isPresent() && !Objects.equals(publisher.getId(), existing.get().getId())) {
            return Optional.empty();
        }
        return Optional.of(publisherRepository.save(publisher));
    }

    @Override
    public List<Publisher> findList(int count) {
        List<Publisher> publisherList = (List<Publisher>) publisherRepository.findAll();
        Random random = new Random();
        List<Publisher> publishers = new ArrayList<>();
        for (int i = 0; i <= count; i++) {
            publishers.add(i, publisherList.get(random.nextInt(0, publisherList.size())));
        }
        return publishers;
    }

}
