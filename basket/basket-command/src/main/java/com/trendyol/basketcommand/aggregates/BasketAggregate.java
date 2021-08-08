package com.trendyol.basketcommand.aggregates;

import com.trendyol.basketcommand.commands.CreateBasketCommand;
import com.trendyol.basketcommand.commands.DeleteBasketCommand;
import com.trendyol.basketcore.events.BasketCreatedEvent;
import com.trendyol.basketcore.events.BasketDeletedEvent;
import com.trendyol.basketcore.models.Basket;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class BasketAggregate {
    @AggregateIdentifier
    private String id;

    private Basket basket;

    @CommandHandler
    public BasketAggregate(CreateBasketCommand command) {
        var event = BasketCreatedEvent.builder()
                .id(command.getId())
                .basket(command.getBasket())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BasketCreatedEvent event) {
        this.id = event.getId();
        this.basket = event.getBasket();
    }

    @CommandHandler
    public void handle(DeleteBasketCommand command) {
        var event = new BasketDeletedEvent();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BasketDeletedEvent event) {
        AggregateLifecycle.markDeleted();
    }

}
