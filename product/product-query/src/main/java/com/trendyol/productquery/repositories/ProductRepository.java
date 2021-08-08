package com.trendyol.productquery.repositories;

import com.trendyol.productcore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}
