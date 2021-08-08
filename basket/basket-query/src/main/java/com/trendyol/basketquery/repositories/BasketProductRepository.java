package com.trendyol.basketquery.repositories;

import com.trendyol.basketcore.models.BasketProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketProductRepository extends JpaRepository<BasketProducts, String> {
    List<BasketProducts> findBasketProductsByProductId(String productId);
    List<BasketProducts> findBasketProductsByBasketId(String basketId);
}
