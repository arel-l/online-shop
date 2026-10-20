
package com.example.kursovaya;

import java.math.BigDecimal;

public abstract class Product {

    private String article;
    private String productName;
    private String category;
    private BigDecimal price;
    private int stockQuantity;

    public Product() {
    }

    public Product(String article, String productName,
                   String category, BigDecimal price,
                   int stockQuantity) {
        this.article = article;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public abstract String getProductType();
}

