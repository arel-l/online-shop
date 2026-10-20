package com.example.kursovaya;

import com.example.kursovaya.repository.impl.InMemoryProductRepository;
import com.example.kursovaya.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        InMemoryProductRepository repository =
                new InMemoryProductRepository();
        productService = new ProductServiceImpl(repository);
    }

    @Test
    void shouldAddAndFindProduct() {
        Product product = new SmartphoneProduct(
                "PHONE-001",
                "Тестовый смартфон",
                "Смартфоны",
                new BigDecimal("50000.00"),
                10
        );

        productService.addProduct(product);

        Optional<Product> found =
                productService.getProductByArticle("PHONE-001");

        assertTrue(found.isPresent());
        assertEquals("Тестовый смартфон", found.get().getProductName());
    }

    @Test
    void shouldRejectDuplicateArticle() {
        Product product = new SmartphoneProduct(
                "PHONE-002",
                "Смартфон",
                "Смартфоны",
                new BigDecimal("30000.00"),
                5
        );

        productService.addProduct(product);

        assertThrows(
                IllegalArgumentException.class,
                () -> productService.addProduct(product)
        );
    }

    @Test
    void shouldUpdateProduct() {
        Product product = new SmartphoneProduct(
                "PHONE-003",
                "Старое название",
                "Смартфоны",
                new BigDecimal("20000.00"),
                3
        );

        productService.addProduct(product);

        Product updated = new SmartphoneProduct(
                "OTHER-ARTICLE",
                "Новое название",
                "Смартфоны",
                new BigDecimal("25000.00"),
                4
        );

        productService.updateProduct("PHONE-003", updated);

        assertEquals(
                "Новое название",
                productService.getProductByArticle("PHONE-003")
                        .orElseThrow()
                        .getProductName()
        );
    }

    @Test
    void shouldDeleteProduct() {
        Product product = new SmartphoneProduct(
                "PHONE-004",
                "Удаляемый смартфон",
                "Смартфоны",
                new BigDecimal("15000.00"),
                2
        );

        productService.addProduct(product);

        assertTrue(productService.deleteProduct("PHONE-004"));
        assertFalse(
                productService.getProductByArticle("PHONE-004").isPresent()
        );
    }

    @Test
    void shouldFilterProductsByMinimumPrice() {
        productService.addProduct(new SmartphoneProduct(
                "FILTER-001",
                "Бюджетный смартфон",
                "Смартфоны",
                new BigDecimal("5000.00"),
                10
        ));

        productService.addProduct(new SmartphoneProduct(
                "FILTER-002",
                "Средний смартфон",
                "Смартфоны",
                new BigDecimal("15000.00"),
                5
        ));

        productService.addProduct(new SmartphoneProduct(
                "FILTER-003",
                "Дорогой смартфон",
                "Смартфоны",
                new BigDecimal("30000.00"),
                2
        ));

        List<Product> filteredProducts = productService.getFilteredProducts(
                null,
                new BigDecimal("10000.00"),
                null
        );

        assertEquals(2, filteredProducts.size());
        assertTrue(filteredProducts.stream().allMatch(
                product -> product.getPrice()
                        .compareTo(new BigDecimal("10000.00")) >= 0
        ));
    }

    @Test
    void shouldFilterProductsByCategoryAndPriceRange() {
        productService.addProduct(new SmartphoneProduct(
                "FILTER-004",
                "Смартфон",
                "Смартфоны",
                new BigDecimal("20000.00"),
                5
        ));

        productService.addProduct(new SmartphoneProduct(
                "FILTER-005",
                "Недорогой смартфон",
                "Смартфоны",
                new BigDecimal("5000.00"),
                8
        ));

        productService.addProduct(new SmartphoneProduct(
                "FILTER-006",
                "Ноутбук",
                "Ноутбуки",
                new BigDecimal("20000.00"),
                3
        ));

        List<Product> filteredProducts = productService.getFilteredProducts(
                "Смартфоны",
                new BigDecimal("10000.00"),
                new BigDecimal("25000.00")
        );

        assertEquals(1, filteredProducts.size());
        assertEquals("FILTER-004", filteredProducts.get(0).getArticle());
    }
}