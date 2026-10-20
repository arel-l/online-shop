package com.example.kursovaya.repository;

import com.example.kursovaya.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Order> findAll();

    Optional<Order> findByOrderNumber(String orderNumber);

    Order save(Order order);

    void deleteByOrderNumber(String orderNumber);

    boolean existsByOrderNumber(String orderNumber);
}

