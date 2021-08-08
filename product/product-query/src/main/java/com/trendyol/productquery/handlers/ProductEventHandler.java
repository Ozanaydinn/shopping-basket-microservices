package com.trendyol.productquery.handlers;

import com.trendyol.productcore.events.PriceUpdatedEvent;
import com.trendyol.productcore.events.ProductCreatedEvent;
import com.trendyol.productcore.events.StockUpdatedEvent;

public interface ProductEventHandler {
    void on(ProductCreatedEvent event);
    void on(PriceUpdatedEvent event);
    void on(StockUpdatedEvent event);
}
