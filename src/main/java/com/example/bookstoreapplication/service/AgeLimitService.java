package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.entity.AgeLimit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AgeLimitService {
    List<AgeLimit> findAll();
}
