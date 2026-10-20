
package com.example.kursovaya;

import java.math.BigDecimal;

public class LaptopProduct extends Product {

    public LaptopProduct() {
    }

    public LaptopProduct(String article, String productName,
                         String category, BigDecimal price,
                         int stockQuantity) {
        super(article, productName, category, price, stockQuantity);
    }

    @Override
    public String getProductType() {
        return "Ноутбук";
    }
}
