
package com.example.kursovaya.controller;

import com.example.kursovaya.Product;
import com.example.kursovaya.exception.InvalidProductDataException;
import com.example.kursovaya.exception.ProductNotFoundException;
import com.example.kursovaya.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger =
            LoggerFactory.getLogger(ProductController.class);

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

        logger.info("Запрос товара с артикулом: {}", article);

        Optional<Product> product =
                productService.getProductByArticle(article);

        if (product.isEmpty()) {
            logger.warn("Товар с артикулом {} не найден", article);
            throw new ProductNotFoundException(article);
        }

        return ResponseEntity.ok(product.get());
    }

    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        logger.info(
                "Запрос списка товаров. Категория: {}, минимальная цена: {}, максимальная цена: {}",
                category,
                minPrice,
                maxPrice
        );

        if (minPrice != null && maxPrice != null
                && minPrice.compareTo(maxPrice) > 0) {

            throw new InvalidProductDataException(
                    "Минимальная цена не может превышать максимальную."
            );
        }

        return productService.getFilteredProducts(
                category, minPrice, maxPrice);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(
            @RequestBody Map<String, Object> request) {

        logger.info("Получен запрос на добавление товара");

        Product product = productRequestMapper.toProduct(
                request, null);

        Product savedProduct = productService.addProduct(product);

        logger.info(
                "Товар успешно добавлен. Артикул: {}",
                savedProduct.getArticle()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedProduct);
    }

    @PutMapping("/{article}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String article,
            @RequestBody Map<String, Object> request) {

        logger.info(
                "Получен запрос на изменение товара с артикулом: {}",
                article
        );

        Product product = productRequestMapper.toProduct(
                request, article);

        Product updatedProduct =
                productService.updateProduct(article, product);

        logger.info(
                "Товар с артикулом {} успешно изменён",
                article
        );

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{article}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable String article) {

        logger.info(
                "Получен запрос на удаление товара с артикулом: {}",
                article
        );

        if (!productService.deleteProduct(article)) {
            logger.warn(
                    "Не удалось удалить товар. Артикул {} не найден",
                    article
            );

            throw new ProductNotFoundException(article);
        }

        logger.info(
                "Товар с артикулом {} успешно удалён",
                article
        );

        return ResponseEntity.noContent().build();
    }
}
