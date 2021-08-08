package com.trendyol.productcore.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockUpdatedEvent {
    private String id;
    private int newStock;
}
