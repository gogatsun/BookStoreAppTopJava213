package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.entity.CoverType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CoverTypeService {

    List<CoverType> findAll();
}
