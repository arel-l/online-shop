package com.example.kursovaya.service.impl;

import com.example.kursovaya.Product;
import com.example.kursovaya.ProductEntity;
import com.example.kursovaya.SmartphoneProduct;
import com.example.kursovaya.LaptopProduct;
import com.example.kursovaya.TelevisionProduct;
import com.example.kursovaya.repository.ProductJpaRepository;
import com.example.kursovaya.service.ProductService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Profile("!test")
public class ProductJpaServiceImpl implements ProductService {

    private final ProductJpaRepository repository;

    public ProductJpaServiceImpl(ProductJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll().stream()
                .map(this::toProduct)
                .toList();
    }

    @Override
    public Optional<Product> getProductByArticle(String article) {
        return repository.findById(article)
                .map(this::toProduct);
    }

    @Override
    public Product addProduct(Product product) {
        if (product == null || product.getArticle() == null
                || product.getArticle().isBlank()) {
            throw new IllegalArgumentException(
                    "Необходимо указать артикул товара");
        }

        if (repository.existsById(product.getArticle())) {
            throw new IllegalArgumentException(
                    "Товар с таким артикулом уже существует");
        }

        return toProduct(repository.save(toEntity(product)));
    }

    @Override
    public Product updateProduct(String article, Product product) {
        if (article == null || article.isBlank() || product == null) {
            throw new IllegalArgumentException(
                    "Необходимо указать артикул и товар");
        }

        if (!repository.existsById(article)) {
            throw new IllegalArgumentException(
                    "Товар не найден: " + article);
        }

        product.setArticle(article);
        return toProduct(repository.save(toEntity(product)));
    }

    @Override
    public boolean deleteProduct(String article) {
        if (article == null || article.isBlank()) {
            throw new IllegalArgumentException(
                    "Артикул товара не может быть пустым");
        }

        if (!repository.existsById(article)) {
            return false;
        }

        repository.deleteById(article);
        return true;
    }
    @Override
    public List<Product> getFilteredProducts(
            String category,
            BigDecimal minPrice,
            BigDecimal maxPrice) {

        return repository.findAll().stream()
                .map(this::toProduct)
                .filter(product ->
                        category == null || category.isBlank()
                                || (product.getCategory() != null
                                && product.getCategory().equalsIgnoreCase(category)))
                .filter(product ->
                        minPrice == null
                                || product.getPrice().compareTo(minPrice) >= 0)
                .filter(product ->
                        maxPrice == null
                                || product.getPrice().compareTo(maxPrice) <= 0)
                .toList();
    }
    private ProductEntity toEntity(Product product) {
        return new ProductEntity(
                product.getArticle(),
                product.getProductName(),
                product.getCategory(),
                product.getPrice(),
                product.getStockQuantity());
    }

    private Product toProduct(ProductEntity entity) {
        String category = entity.getCategory() == null
                ? ""
                : entity.getCategory().toLowerCase();

        if (category.contains("смартфон")) {
            return new SmartphoneProduct(
                    entity.getArticle(), entity.getProductName(),
                    entity.getCategory(), entity.getPrice(),
                    entity.getStockQuantity());
        }

        if (category.contains("ноутбук")) {
            return new LaptopProduct(
                    entity.getArticle(), entity.getProductName(),
                    entity.getCategory(), entity.getPrice(),
                    entity.getStockQuantity());
        }

        if (category.contains("телевизор")) {
            return new TelevisionProduct(
                    entity.getArticle(), entity.getProductName(),
                    entity.getCategory(), entity.getPrice(),
                    entity.getStockQuantity());
        }

        return new SmartphoneProduct(
                entity.getArticle(), entity.getProductName(),
                entity.getCategory(), entity.getPrice(),
                entity.getStockQuantity());
    }
}
