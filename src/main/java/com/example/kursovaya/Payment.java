
package com.example.kursovaya;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {

    private String transactionNumber;
    private String orderNumber;
    private LocalDate paymentDate;
    private String paymentMethod;
    private BigDecimal amount;

    public Payment() {
    }

    public Payment(String transactionNumber, String orderNumber,
                   LocalDate paymentDate, String paymentMethod,
                   BigDecimal amount) {
        this.transactionNumber = transactionNumber;
        this.orderNumber = orderNumber;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
