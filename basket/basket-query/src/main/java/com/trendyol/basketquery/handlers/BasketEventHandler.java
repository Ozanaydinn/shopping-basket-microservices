package com.trendyol.basketquery.handlers;

import com.trendyol.basketcore.events.BasketCreatedEvent;
import com.trendyol.basketcore.events.BasketDeletedEvent;

public interface BasketEventHandler {
    void on(BasketCreatedEvent event);
    void on(BasketDeletedEvent event);
}
