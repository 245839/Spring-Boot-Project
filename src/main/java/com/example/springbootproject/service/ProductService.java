package com.example.springbootproject.service;

import com.example.springbootproject.entity.Product;
import com.example.springbootproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public Page<Product> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<Product> findByCategory(Long categoryId, Pageable pageable) {
        return repo.findByCategoryId(categoryId.intValue(), pageable);
    }
}
