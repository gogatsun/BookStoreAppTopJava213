package com.example.bookstoreapplication.rdb.service;

import com.example.bookstoreapplication.entity.CoverType;
import com.example.bookstoreapplication.rdb.repositories.CoverTypeRepository;
import com.example.bookstoreapplication.service.CoverTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoverTypeRdbService implements CoverTypeService {
    private final CoverTypeRepository coverTypeRepository;

    public CoverTypeRdbService(CoverTypeRepository coverTypeRepository) {
        this.coverTypeRepository = coverTypeRepository;
    }

    @Override
    public List<CoverType> findAll() {
        return (List<CoverType>) coverTypeRepository.findAll();
    }
}
