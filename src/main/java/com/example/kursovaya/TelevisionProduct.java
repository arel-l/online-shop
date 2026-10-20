
package com.example.kursovaya;

import java.math.BigDecimal;

public class TelevisionProduct extends Product {

    public TelevisionProduct() {
    }

    public TelevisionProduct(String article, String productName,
                             String category, BigDecimal price,
                             int stockQuantity) {
        super(article, productName, category, price, stockQuantity);
    }

    @Override
    public String getProductType() {
        return "Телевизор";
    }
}
