package com.trendyol.basketcore.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRemovedFromBasketEvent {
    private String id;
    private String basketId;
    private String productId;
}
