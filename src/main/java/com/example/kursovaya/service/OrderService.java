
package com.example.kursovaya.service;

import com.example.kursovaya.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> getAllOrders();

    Optional<Order> getOrderByNumber(String orderNumber);

    Order createOrder(Order order);

    Order updateOrderStatus(String orderNumber, String status);

    boolean deleteOrder(String orderNumber);
}

