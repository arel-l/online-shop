package com.example.kursovaya.controller;
import com.example.kursovaya.*;
import com.example.kursovaya.repository.ProductJpaRepository;
import com.example.kursovaya.service.ProductService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{article}")
    public ResponseEntity<Product> getProductByArticle(
            @PathVariable String article) {

        return productService.getProductByArticle(article)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        return productService.getFilteredProducts(
                category, minPrice, maxPrice
        );
    }
    @PostMapping
    public ResponseEntity<?> addProduct(
            @RequestBody Map<String, Object> request) {

        try {
            String article = (String) request.get("article");
            String productName = (String) request.get("productName");
            String category = (String) request.get("category");
            BigDecimal price = new BigDecimal(
                    request.get("price").toString());
            int stockQuantity = Integer.parseInt(
                    request.get("stockQuantity").toString());
            String productType = (String) request.get("productType");

            Product product;

            switch (productType.toUpperCase()) {
                case "SMARTPHONE":
                    product = new SmartphoneProduct(
                            article, productName, category,
                            price, stockQuantity);
                    break;

                case "LAPTOP":
                    product = new LaptopProduct(
                            article, productName, category,
                            price, stockQuantity);
                    break;

                case "TELEVISION":
                    product = new TelevisionProduct(
                            article, productName, category,
                            price, stockQuantity);
                    break;

                default:
                    return ResponseEntity.badRequest()
                            .body("Неизвестный тип товара");
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(productService.addProduct(product));

        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{article}")
    public ResponseEntity<?> updateProduct(
            @PathVariable String article,
            @RequestBody Map<String, Object> request) {

        try {
            String productName = (String) request.get("productName");
            String category = (String) request.get("category");
            BigDecimal price = new BigDecimal(
                    request.get("price").toString());
            int stockQuantity = Integer.parseInt(
                    request.get("stockQuantity").toString());
            String productType = (String) request.get("productType");

            Product product;

            switch (productType.toUpperCase()) {
                case "SMARTPHONE":
                    product = new SmartphoneProduct(
                            article, productName, category,
                            price, stockQuantity);
                    break;

                case "LAPTOP":
                    product = new LaptopProduct(
                            article, productName, category,
                            price, stockQuantity);
                    break;

                case "TELEVISION":
                    product = new TelevisionProduct(
                            article, productName, category,
                            price, stockQuantity);
                    break;

                default:
                    return ResponseEntity.badRequest()
                            .body("Неизвестный тип товара");
            }

            return ResponseEntity.ok(
                    productService.updateProduct(article, product));

        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{article}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable String article) {

        if (productService.deleteProduct(article)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
