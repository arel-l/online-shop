
package com.example.kursovaya.repository.impl;

import com.example.kursovaya.Product;
import com.example.kursovaya.repository.ProductRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("test")
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = new ArrayList<>();

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    @Override
    public Optional<Product> findByArticle(String article) {
        return products.stream()
                .filter(product -> product.getArticle().equals(article))
                .findFirst();
    }

    @Override
    public Product save(Product product) {
        Optional<Product> existingProduct =
                findByArticle(product.getArticle());

        if (existingProduct.isPresent()) {
            products.remove(existingProduct.get());
        }

        products.add(product);
        return product;
    }

    @Override
    public void deleteByArticle(String article) {
        products.removeIf(
                product -> product.getArticle().equals(article));
    }

    @Override
    public boolean existsByArticle(String article) {
        return findByArticle(article).isPresent();
    }
}
