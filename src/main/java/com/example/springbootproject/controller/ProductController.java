package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Product;
import com.example.springbootproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

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
