package com.example.kursovaya.exception.handler;

import com.example.kursovaya.exception.InvalidProductDataException;
import com.example.kursovaya.exception.ProductAlreadyExistsException;
import com.example.kursovaya.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void shouldHandleProductNotFoundException() {
        ProductNotFoundException exception =
                new ProductNotFoundException("PHONE-001");

        ResponseEntity<String> response =
                exceptionHandler.handleProductNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("PHONE-001"));
        assertTrue(response.getBody().contains("не найден"));
    }

    @Test
    void shouldHandleProductAlreadyExistsException() {
        ProductAlreadyExistsException exception =
                new ProductAlreadyExistsException("PHONE-002");

        ResponseEntity<String> response =
                exceptionHandler.handleProductAlreadyExists(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody().contains("PHONE-002"));
        assertTrue(response.getBody().contains("уже существует"));
    }

    @Test
    void shouldHandleInvalidProductDataException() {
        InvalidProductDataException exception =
                new InvalidProductDataException(
                        "Некорректная цена товара.");

        ResponseEntity<String> response =
                exceptionHandler.handleInvalidProductData(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Некорректная цена товара.", response.getBody());
    }

    @Test
    void shouldHandleUnexpectedException() {
        Exception exception =
                new RuntimeException("Unexpected error");

        ResponseEntity<String> response =
                exceptionHandler.handleUnexpectedException(exception);

        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                response.getStatusCode()
        );

        assertTrue(response.getBody().contains(
                "Произошла внутренняя ошибка сервера."
        ));
    }
}
