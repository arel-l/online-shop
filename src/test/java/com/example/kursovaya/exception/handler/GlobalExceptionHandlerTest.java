package com.example.kursovaya.exception.handler;

import com.example.kursovaya.exception.InvalidProductDataException;
import com.example.kursovaya.exception.ProductAlreadyExistsException;
import com.example.kursovaya.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler =
            new GlobalExceptionHandler();

    @Test
    void shouldHandleProductNotFound() {
        ProductNotFoundException exception =
                new ProductNotFoundException("TEST-001");

        ResponseEntity<String> response =
                exceptionHandler.handleProductNotFound(exception);

        assertEquals(
                HttpStatus.NOT_FOUND,
                response.getStatusCode()
        );

        assertEquals(
                "Товар с артикулом 'TEST-001' не найден. Проверьте правильность артикула.",
                response.getBody()
        );
    }

    @Test
    void shouldHandleProductAlreadyExists() {
        ProductAlreadyExistsException exception =
                new ProductAlreadyExistsException("TEST-001");

        ResponseEntity<String> response =
                exceptionHandler.handleProductAlreadyExists(exception);

        assertEquals(
                HttpStatus.CONFLICT,
                response.getStatusCode()
        );

        assertEquals(
                "Товар с артикулом 'TEST-001' уже существует. Используйте другой артикул.",
                response.getBody()
        );
    }

    @Test
    void shouldHandleInvalidProductData() {
        InvalidProductDataException exception =
                new InvalidProductDataException(
                        "Некорректные данные товара"
                );

        ResponseEntity<String> response =
                exceptionHandler.handleInvalidProductData(exception);

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertEquals(
                "Некорректные данные товара",
                response.getBody()
        );
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
                "Произошла внутренняя ошибка сервера:"
        ));

        assertTrue(response.getBody().contains(
                "Обратитесь к администратору."
        ));
    }
}
