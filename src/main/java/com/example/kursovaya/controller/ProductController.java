package com.example.kursovaya.controller;

import com.example.kursovaya.Product;
import com.example.kursovaya.exception.ProductNotFoundException;
import com.example.kursovaya.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductRequestMapper productRequestMapper;

    public ProductController(
            ProductService productService,
            ProductRequestMapper productRequestMapper) {
        this.productService = productService;
        this.productRequestMapper = productRequestMapper;
    }

    @GetMapping("/{article}")
    public ResponseEntity<Product> getProductByArticle(
            @PathVariable String article) {

        Optional<Product> product =
                productService.getProductByArticle(article);

        if (product.isEmpty()) {
            throw new ProductNotFoundException(article);
        }

        return ResponseEntity.ok(product.get());
    }

    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        if (minPrice != null && maxPrice != null
                && minPrice.compareTo(maxPrice) > 0) {
            throw new com.example.kursovaya.exception.InvalidProductDataException(
                    "Минимальная цена не может превышать максимальную.");
        }

        return productService.getFilteredProducts(
                category, minPrice, maxPrice);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(
            @RequestBody Map<String, Object> request) {

        Product product = productRequestMapper.toProduct(
                request, null);

        Product savedProduct = productService.addProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedProduct);
    }

    @PutMapping("/{article}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String article,
            @RequestBody Map<String, Object> request) {

        Product product = productRequestMapper.toProduct(
                request, article);

        Product updatedProduct =
                productService.updateProduct(article, product);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{article}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable String article) {

        if (!productService.deleteProduct(article)) {
            throw new ProductNotFoundException(article);
        }

        return ResponseEntity.noContent().build();
    }
}
