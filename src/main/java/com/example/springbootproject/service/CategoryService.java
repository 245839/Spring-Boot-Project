package com.example.springbootproject.service;

import com.example.springbootproject.entity.Category;
import com.example.springbootproject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public Page<Category> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<Category> findByName(String name, Pageable pageable) {
        return repo.findByCategoryNameContainingIgnoreCase(name, pageable);
    }
}