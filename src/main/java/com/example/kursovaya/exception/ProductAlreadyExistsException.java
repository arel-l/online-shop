package com.example.kursovaya.exception;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException(String article) {
        super("Товар с артикулом '" + article
                + "' уже существует. Используйте другой артикул.");
    }
}