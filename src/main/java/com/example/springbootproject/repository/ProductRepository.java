package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductName(String productName);
    Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);
}
