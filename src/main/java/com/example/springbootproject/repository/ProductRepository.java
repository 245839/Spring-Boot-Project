package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductName(String productName);
}
