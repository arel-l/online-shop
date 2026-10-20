
package com.example.kursovaya.repository.impl;

import com.example.kursovaya.Order;
import com.example.kursovaya.repository.OrderRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("test")
public class InMemoryOrderRepository implements OrderRepository {

    private final List<Order> orders = new ArrayList<>();

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orders);
    }

    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return orders.stream()
                .filter(order ->
                        order.getOrderNumber().equals(orderNumber))
                .findFirst();
    }

    @Override
    public Order save(Order order) {
        Optional<Order> existingOrder =
                findByOrderNumber(order.getOrderNumber());

        if (existingOrder.isPresent()) {
            orders.remove(existingOrder.get());
        }

        orders.add(order);
        return order;
    }

    @Override
    public void deleteByOrderNumber(String orderNumber) {
        orders.removeIf(
                order -> order.getOrderNumber().equals(orderNumber));
    }

    @Override
    public boolean existsByOrderNumber(String orderNumber) {
        return findByOrderNumber(orderNumber).isPresent();
    }
}

