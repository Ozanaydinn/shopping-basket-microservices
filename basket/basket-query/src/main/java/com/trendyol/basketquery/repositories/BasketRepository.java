package com.trendyol.basketquery.repositories;

import com.trendyol.basketcore.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, String> {
}
