
package com.example.kursovaya;

import java.math.BigDecimal;

public class OrderItem {

    private String orderNumber;
    private Product product;
    private int quantity;
    private BigDecimal price;

    public OrderItem() {
    }

    public OrderItem(String orderNumber, Product product,
                     int quantity, BigDecimal price) {
        this.orderNumber = orderNumber;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal calculateSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
