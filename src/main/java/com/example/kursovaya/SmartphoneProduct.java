
package com.example.kursovaya;

import java.math.BigDecimal;

public class SmartphoneProduct extends Product {

    public SmartphoneProduct() {
    }

    public SmartphoneProduct(String article, String productName,
                             String category, BigDecimal price,
                             int stockQuantity) {
        super(article, productName, category, price, stockQuantity);
    }

    @Override
    public String getProductType() {
        return "Смартфон";
    }
}

