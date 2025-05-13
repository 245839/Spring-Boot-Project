package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Category;
import com.example.springbootproject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{categoryName}")
    public Category getCategoryByName(@PathVariable("categoryName") String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    @PostMapping("/{categoryName}")
    public Category addCategory(@PathVariable("categoryName") String categoryName) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        return categoryRepository.save(category);
    }

    @PutMapping("/{categoryId}/{categoryName}")
    public Category updateCategory(@PathVariable("categoryId") Integer categoryId, @PathVariable("categoryName") String categoryName) {
        return categoryRepository.findById(categoryId).map(category -> {
            category.setCategoryName(categoryName);
            return categoryRepository.save(category);
        }).orElse(null);
    }

    @DeleteMapping("/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
            return "Kategoria została usunięta.";
        } else {
            return "Kategoria o podanym ID nie istnieje.";
        }
    }
}