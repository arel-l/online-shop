package com.example.kursovaya.service;

import com.example.kursovaya.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAllProducts();

    Optional<Product> getProductByArticle(String article);

    Product addProduct(Product product);

    Product updateProduct(String article, Product product);

    boolean deleteProduct(String article);

    List<Product> getFilteredProducts(
            String category,
            BigDecimal minPrice,
            BigDecimal maxPrice
    );
}