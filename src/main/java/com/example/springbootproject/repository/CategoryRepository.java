package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Page<Category> findByCategoryNameContainingIgnoreCase(String name, Pageable pageable);
    Category findByCategoryName(String name);
}
