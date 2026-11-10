package com.example.kursovaya.service.impl;

import com.example.kursovaya.Product;
import com.example.kursovaya.exception.InvalidProductDataException;
import com.example.kursovaya.exception.ProductAlreadyExistsException;
import com.example.kursovaya.exception.ProductNotFoundException;
import com.example.kursovaya.repository.ProductRepository;
import com.example.kursovaya.service.ProductService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Profile("test")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductByArticle(String article) {
        return productRepository.findByArticle(article);
    }

    @Override
    public Product addProduct(Product product) {
        if (product == null) {
            throw new InvalidProductDataException(
                    "Необходимо передать данные товара.");
        }

        if (product.getArticle() == null
                || product.getArticle().isBlank()) {
            throw new InvalidProductDataException(
                    "Артикул товара не может быть пустым.");
        }

        if (productRepository.existsByArticle(product.getArticle())) {
            throw new ProductAlreadyExistsException(
                    product.getArticle());
        }

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(String article, Product product) {
        if (article == null || article.isBlank()) {
            throw new InvalidProductDataException(
                    "Необходимо указать артикул товара.");
        }

        if (product == null) {
            throw new InvalidProductDataException(
                    "Необходимо передать данные для обновления товара.");
        }

        Optional<Product> existingProduct =
                productRepository.findByArticle(article);

        if (existingProduct.isEmpty()) {
            throw new ProductNotFoundException(article);
        }

        product.setArticle(article);

        return productRepository.save(product);
    }

    @Override
    public boolean deleteProduct(String article) {
        if (article == null || article.isBlank()) {
            throw new InvalidProductDataException(
                    "Артикул товара не может быть пустым.");
        }

        if (!productRepository.existsByArticle(article)) {
            return false;
        }

        productRepository.deleteByArticle(article);

        return true;
    }

    @Override
    public List<Product> getFilteredProducts(
            String category,
            BigDecimal minPrice,
            BigDecimal maxPrice) {

        return getAllProducts().stream()
                .filter(product ->
                        category == null || category.isBlank()
                                || (product.getCategory() != null
                                && product.getCategory()
                                .equalsIgnoreCase(category)))
                .filter(product ->
                        minPrice == null
                                || product.getPrice()
                                .compareTo(minPrice) >= 0)
                .filter(product ->
                        maxPrice == null
                                || product.getPrice()
                                .compareTo(maxPrice) <= 0)
                .toList();
    }
}
