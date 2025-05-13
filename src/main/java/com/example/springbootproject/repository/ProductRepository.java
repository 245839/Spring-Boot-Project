package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);
}
