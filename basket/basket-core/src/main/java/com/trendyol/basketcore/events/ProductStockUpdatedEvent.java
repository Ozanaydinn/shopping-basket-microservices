package com.trendyol.basketcore.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductStockUpdatedEvent {
    private String id;
    private String basketProductId;
    private String productId;
    private int newStock;
}
