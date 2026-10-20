package com.example.kursovaya;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private String orderNumber;
    private String customerPhone;
    private LocalDate orderData;
    private String status;
    private BigDecimal totalAmount;
    private String employeeNumber;
    private List<OrderItem> items = new ArrayList<>();

    public Order() {
    }

    public Order(String orderNumber, String customerPhone,
                 LocalDate orderData, String status,
                 BigDecimal totalAmount, String employeeNumber) {
        this.orderNumber = orderNumber;
        this.customerPhone = customerPhone;
        this.orderData = orderData;
        this.status = status;
        this.totalAmount = totalAmount;
        this.employeeNumber = employeeNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public LocalDate getOrderData() {
        return orderData;
    }

    public void setOrderData(LocalDate orderData) {
        this.orderData = orderData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
