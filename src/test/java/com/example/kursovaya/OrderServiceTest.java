package com.example.kursovaya;

import com.example.kursovaya.repository.impl.InMemoryOrderRepository;
import com.example.kursovaya.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        InMemoryOrderRepository repository =
                new InMemoryOrderRepository();
        orderService = new OrderServiceImpl(repository);
    }

    private Order createTestOrder() {
        Order order = new Order();
        order.setOrderNumber("ORDER-001");
        order.setCustomerPhone("+79990000000");
        order.setStatus("Новый");
        return order;
    }

    @Test
    void shouldCreateAndFindOrder() {
        Order order = createTestOrder();

        orderService.createOrder(order);

        assertTrue(
                orderService.getOrderByNumber("ORDER-001").isPresent()
        );
    }

    @Test
    void shouldRejectDuplicateOrderNumber() {
        orderService.createOrder(createTestOrder());

        assertThrows(
                IllegalArgumentException.class,
                () -> orderService.createOrder(createTestOrder())
        );
    }

    @Test
    void shouldUpdateOrderStatus() {
        orderService.createOrder(createTestOrder());

        Order updated = orderService.updateOrderStatus(
                "ORDER-001",
                "В обработке"
        );

        assertEquals("В обработке", updated.getStatus());
    }

    @Test
    void shouldDeleteOrder() {
        orderService.createOrder(createTestOrder());

        assertTrue(orderService.deleteOrder("ORDER-001"));
        assertFalse(
                orderService.getOrderByNumber("ORDER-001").isPresent()
        );
    }

    @Test
    void shouldReturnEmptyForUnknownOrder() {
        assertTrue(
                orderService.getOrderByNumber("UNKNOWN-ORDER").isEmpty()
        );
    }

    @Test
    void shouldRejectInvalidOrderStatusUpdate() {
        assertThrows(
                IllegalArgumentException.class,
                () -> orderService.updateOrderStatus(
                        "UNKNOWN-ORDER",
                        "В обработке"
                )
        );
    }
}

