
package com.example.kursovaya.repository;

import com.example.kursovaya.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findByArticle(String article);

    Product save(Product product);

    void deleteByArticle(String article);

    boolean existsByArticle(String article);
}

