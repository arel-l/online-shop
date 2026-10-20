
package com.example.kursovaya;

import java.time.LocalDate;

public class ReturnItem {

    private String returnNumber;
    private String orderNumber;
    private String article;
    private LocalDate returnDate;
    private int quantity;
    private String reason;

    public ReturnItem() {
    }

    public ReturnItem(String returnNumber, String orderNumber,
                      String article, LocalDate returnDate,
                      int quantity, String reason) {
        this.returnNumber = returnNumber;
        this.orderNumber = orderNumber;
        this.article = article;
        this.returnDate = returnDate;
        this.quantity = quantity;
        this.reason = reason;
    }

    public String getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(String returnNumber) {
        this.returnNumber = returnNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
