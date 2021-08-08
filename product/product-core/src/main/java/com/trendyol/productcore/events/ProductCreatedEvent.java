package com.trendyol.productcore.events;

import com.trendyol.productcore.models.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCreatedEvent {
    private String id;
    private Product product;
}
