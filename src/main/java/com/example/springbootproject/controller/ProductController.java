package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Product;
import com.example.springbootproject.repository.ProductRepository;
import com.example.springbootproject.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository productRepository;

    // GET /api/products?page=0&size=10&sort=name,asc
    @GetMapping
    public Page<Product> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    // GET /api/products/by-category?categoryId=3&page=0&size=10
    @GetMapping("/by-category")
    public Page<Product> getByCategory(
            @RequestParam Integer categoryId,
            Pageable pageable) {
        return service.findByCategory(categoryId.longValue(), pageable);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{productName}")
    public Product getProductByName(@PathVariable("productName") String productName) {
        return productRepository.findByProductName(productName);
    }

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Integer productId) {
        if (!productRepository.existsById(productId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produkt o ID " + productId + " nie istnieje.");
        }
        productRepository.deleteById(productId);
        return ResponseEntity.ok("Produkt usunięty.");
    }
}
