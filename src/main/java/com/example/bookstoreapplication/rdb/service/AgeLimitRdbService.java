package com.example.bookstoreapplication.rdb.service;

import com.example.bookstoreapplication.entity.AgeLimit;
import com.example.bookstoreapplication.rdb.repositories.AgeLimitRepository;
import com.example.bookstoreapplication.service.AgeLimitService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgeLimitRdbService implements AgeLimitService {
    private final AgeLimitRepository ageLimitRepository;

    public AgeLimitRdbService(AgeLimitRepository ageLimitRepository) {
        this.ageLimitRepository = ageLimitRepository;
    }

    @Override
    public List<AgeLimit> findAll() {
        return (List<AgeLimit>) ageLimitRepository.findAll();
    }
}
