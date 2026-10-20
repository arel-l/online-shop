package com.example.kursovaya.service.impl;

import com.example.kursovaya.Order;
import com.example.kursovaya.repository.OrderRepository;
import com.example.kursovaya.service.OrderService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile("test")
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderByNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    @Override
    public Order createOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Заказ не может быть null");
        }

        if (order.getOrderNumber() == null
                || order.getOrderNumber().isBlank()) {
            throw new IllegalArgumentException(
                    "Номер заказа не может быть пустым");
        }

        if (orderRepository.existsByOrderNumber(order.getOrderNumber())) {
            throw new IllegalArgumentException(
                    "Заказ с таким номером уже существует");
        }

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderStatus(String orderNumber, String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException(
                    "Статус заказа не может быть пустым");
        }

        Optional<Order> foundOrder =
                orderRepository.findByOrderNumber(orderNumber);

        if (foundOrder.isEmpty()) {
            throw new IllegalArgumentException(
                    "Заказ не найден: " + orderNumber);
        }

        Order order = foundOrder.get();
        order.setStatus(status);

        return orderRepository.save(order);
    }

    @Override
    public boolean deleteOrder(String orderNumber) {
        if (!orderRepository.existsByOrderNumber(orderNumber)) {
            return false;
        }

        orderRepository.deleteByOrderNumber(orderNumber);
        return true;
    }
}

