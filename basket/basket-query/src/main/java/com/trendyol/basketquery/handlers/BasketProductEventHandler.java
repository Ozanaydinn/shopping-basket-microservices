package com.trendyol.basketquery.handlers;

import com.trendyol.basketcore.events.ProductAddedToBasketEvent;
import com.trendyol.basketcore.events.ProductPriceUpdatedEvent;
import com.trendyol.basketcore.events.ProductStockUpdatedEvent;

public interface BasketProductEventHandler {
    void on(ProductAddedToBasketEvent event);
    void on(ProductPriceUpdatedEvent event);
    void on(ProductStockUpdatedEvent event);

}
