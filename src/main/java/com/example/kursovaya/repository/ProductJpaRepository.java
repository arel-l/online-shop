
package com.example.kursovaya.repository;

import com.example.kursovaya.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductJpaRepository
        extends JpaRepository<ProductEntity, String> {

    List<ProductEntity> findByCategoryIgnoreCase(String category);

    List<ProductEntity> findByPriceGreaterThanEqual(BigDecimal minPrice);

    List<ProductEntity> findByPriceLessThanEqual(BigDecimal maxPrice);

    List<ProductEntity> findByPriceBetween(
            BigDecimal minPrice,
            BigDecimal maxPrice
    );
}

