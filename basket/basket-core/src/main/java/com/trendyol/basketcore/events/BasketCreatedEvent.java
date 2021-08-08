package com.trendyol.basketcore.events;

import com.trendyol.basketcore.models.Basket;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasketCreatedEvent {
    private String id;
    private Basket basket;
}
