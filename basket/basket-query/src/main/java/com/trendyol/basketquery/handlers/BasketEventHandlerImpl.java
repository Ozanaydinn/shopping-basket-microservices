package com.trendyol.basketquery.handlers;

import com.trendyol.basketcore.events.BasketCreatedEvent;
import com.trendyol.basketcore.events.BasketDeletedEvent;
import com.trendyol.basketquery.repositories.BasketRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("basket-group")
public class BasketEventHandlerImpl implements BasketEventHandler{
    private final BasketRepository basketRepository;

    @Autowired
    public BasketEventHandlerImpl(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @EventHandler
    @Override
    public void on(BasketCreatedEvent event) {
        var basket = event.getBasket();
        var savedBasket = basketRepository.save(basket);
        System.out.println("Basket saved with id: " + savedBasket.getId());
    }

    @EventHandler
    @Override
    public void on(BasketDeletedEvent event) {
        basketRepository.deleteById(event.getId());
    }
}
