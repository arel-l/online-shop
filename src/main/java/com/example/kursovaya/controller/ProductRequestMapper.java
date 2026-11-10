package com.example.kursovaya.controller;

import com.example.kursovaya.LaptopProduct;
import com.example.kursovaya.Product;
import com.example.kursovaya.SmartphoneProduct;
import com.example.kursovaya.TelevisionProduct;
import com.example.kursovaya.exception.InvalidProductDataException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ProductRequestMapper {

    public Product toProduct(
            Map<String, Object> request,
            String article) {

        if (request == null) {
            throw new InvalidProductDataException(
                    "Необходимо передать данные товара.");
        }

        String productName = getString(request, "productName");
        String category = getString(request, "category");
        String productType = getString(request, "productType");
        BigDecimal price = getPrice(request);
        int stockQuantity = getStockQuantity(request);

        if (article == null || article.isBlank()) {
            article = getString(request, "article");
        }

        if (article == null || article.isBlank()) {
            throw new InvalidProductDataException(
                    "Необходимо указать артикул товара.");
        }

        switch (productType.toUpperCase()) {
            case "SMARTPHONE":
            case "СМАРТФОН":
                return new SmartphoneProduct(
                        article, productName, category,
                        price, stockQuantity);

            case "LAPTOP":
            case "НОУТБУК":
                return new LaptopProduct(
                        article, productName, category,
                        price, stockQuantity);

            case "TELEVISION":
            case "ТЕЛЕВИЗОР":
                return new TelevisionProduct(
                        article, productName, category,
                        price, stockQuantity);

            default:
                throw new InvalidProductDataException(
                        "Неизвестный тип товара: " + productType);
        }
    }

    private String getString(
            Map<String, Object> request,
            String fieldName) {

        Object value = request.get(fieldName);

        if (value == null || value.toString().isBlank()) {
            throw new InvalidProductDataException(
                    "Поле '" + fieldName + "' обязательно для заполнения.");
        }

        return value.toString();
    }

    private BigDecimal getPrice(Map<String, Object> request) {
        try {
            BigDecimal price = new BigDecimal(
                    request.get("price").toString());

            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvalidProductDataException(
                        "Цена товара должна быть больше нуля.");
            }

            return price;
        } catch (NumberFormatException | NullPointerException exception) {
            throw new InvalidProductDataException(
                    "Цена товара должна быть указана числом.");
        }
    }

    private int getStockQuantity(Map<String, Object> request) {
        try {
            int quantity = Integer.parseInt(
                    request.get("stockQuantity").toString());

            if (quantity < 0) {
                throw new InvalidProductDataException(
                        "Количество товара не может быть отрицательным.");
            }

            return quantity;
        } catch (NumberFormatException | NullPointerException exception) {
            throw new InvalidProductDataException(
                    "Количество товара должно быть целым числом.");
        }
    }
}
