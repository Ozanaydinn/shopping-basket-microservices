package com.trendyol.productcore.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceUpdatedEvent {
    private String id;
    private Long newPrice;
}
