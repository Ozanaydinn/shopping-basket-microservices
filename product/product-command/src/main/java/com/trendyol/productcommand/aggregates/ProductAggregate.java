package com.trendyol.productcommand.aggregates;

import com.trendyol.productcommand.commands.CreateProductCommand;
import com.trendyol.productcommand.commands.UpdatePriceCommand;
import com.trendyol.productcommand.commands.UpdateStockCommand;
import com.trendyol.productcore.events.PriceUpdatedEvent;
import com.trendyol.productcore.events.ProductCreatedEvent;
import com.trendyol.productcore.events.StockUpdatedEvent;
import com.trendyol.productcore.models.Product;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class ProductAggregate {
    @AggregateIdentifier
    private String id;

    private Product product;

    @CommandHandler
    public ProductAggregate(CreateProductCommand command) {
        var event = ProductCreatedEvent.builder()
                .id(command.getId())
                .product(command.getProduct())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.id = event.getId();
        this.product = event.getProduct();
    }

    @CommandHandler
    public void handle(UpdatePriceCommand command) {
        var newPrice = command.getNewPrice();
        var event = PriceUpdatedEvent.builder()
                .id(command.getId())
                .newPrice(newPrice)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PriceUpdatedEvent event) {
        this.id = event.getId();
        this.product.setPrice(event.getNewPrice());
    }

    @CommandHandler
    public void handle(UpdateStockCommand command) {
        var newStock = command.getNewStock();
        var event = StockUpdatedEvent.builder()
                .id(command.getId())
                .newStock(newStock)
                .build();
        // You can apply logic here, maybe do so while implementing rabbitmq or something

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(StockUpdatedEvent event) {
        this.id = event.getId();
        this.product.setStock(event.getNewStock());
    }
}
