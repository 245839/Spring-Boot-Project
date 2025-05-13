package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Category;
import com.example.springbootproject.repository.CategoryRepository;
import com.example.springbootproject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @Autowired
    private CategoryRepository categoryRepository;

    // GET /api/categories?page=0&size=5&sort=categoryName,asc
    @GetMapping
    public Page<Category> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    // GET /api/categories/search?name=beverage&page=0&size=5&sort=categoryName,asc
    @GetMapping("/search")
    public Page<Category> searchByName(
            @RequestParam String name,
            Pageable pageable) {
        return service.findByName(name, pageable);
    }

    // GET /api/categories/all
    @GetMapping("/all")
    public List<Category> getAllUnpaged() {
        return categoryRepository.findAll();
    }

    // GET /api/categories/get?name=Seafood
    @GetMapping("/get")
    public Category getByName(@RequestParam String name) {
        return categoryRepository.findByCategoryName(name);
    }

    // POST /api/categories
    @PostMapping
    public Category addCategory(@RequestParam String name) {
        Category c = new Category();
        c.setCategoryName(name);
        return categoryRepository.save(c);
    }

    // PUT /api/categories/{id}
    @PutMapping("/{id}")
    public Category updateCategory(
            @PathVariable Integer id,
            @RequestParam String name) {
        return categoryRepository.findById(id)
                .map(cat -> {
                    cat.setCategoryName(name);
                    return categoryRepository.save(cat);
                })
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    // DELETE /api/categories/{id}
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        if (!categoryRepository.existsById(id)) {
            return "Kategoria o ID " + id + " nie istnieje.";
        }
        categoryRepository.deleteById(id);
        return "Kategoria usunięta.";
    }
}
