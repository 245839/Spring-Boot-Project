package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Category;
import com.example.springbootproject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/get")
    public Category getCategoryByName(@RequestParam String name) {
        return categoryRepository.findByCategoryName(name);
    }

    @GetMapping("/add")
    public Category addCategory(@RequestParam String name) {
        Category category = new Category();
        category.setCategoryName(name);
        return categoryRepository.save(category);
    }

    @GetMapping("/update")
    public Category updateCategory(@RequestParam Integer id, @RequestParam String name) {
        return categoryRepository.findById(id).map(category -> {
            category.setCategoryName(name);
            return categoryRepository.save(category);
        }).orElse(null);
    }

    @GetMapping("/delete")
    public String deleteCategory(@RequestParam Integer id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return "Kategoria została usunięta.";
        } else {
            return "Kategoria o podanym ID nie istnieje.";
        }
    }
}