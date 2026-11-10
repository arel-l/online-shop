package com.example.kursovaya.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String article) {
        super("Товар с артикулом '" + article
                + "' не найден. Проверьте правильность артикула.");
    }
}
