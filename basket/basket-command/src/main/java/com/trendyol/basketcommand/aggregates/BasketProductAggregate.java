package com.trendyol.basketcommand.aggregates;

import com.trendyol.basketcommand.commands.AddProductToBasketCommand;
import com.trendyol.basketcommand.commands.UpdateProductPriceCommand;
import com.trendyol.basketcommand.commands.UpdateProductStockCommand;
import com.trendyol.basketcore.events.ProductAddedToBasketEvent;
import com.trendyol.basketcore.events.ProductPriceUpdatedEvent;
import com.trendyol.basketcore.events.ProductStockUpdatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Aggregate
@NoArgsConstructor
public class BasketProductAggregate {
    @AggregateIdentifier
    private String id;

    private String basketId;

    private String productId;

    private String productTitle;

    private int productStock;

    private Long productPrice;

    private String imageUrl;


    @CommandHandler
    public BasketProductAggregate(AddProductToBasketCommand command) {
        System.out.println("HERE HERE");
        var event = ProductAddedToBasketEvent.builder()
                .id(command.getId())
                .basketId(command.getBasketId())
                .productId(command.getProductId())
                .productTitle(command.getProductTitle())
                .productPrice(command.getProductPrice())
                .productStock(command.getProductStock())
                .imageUrl(command.getImageUrl())
                .build();
        System.out.println(event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ProductAddedToBasketEvent event) {
        this.id = event.getId();
        this.basketId = event.getBasketId();
        this.productId = event.getProductId();
        this.productTitle = event.getProductTitle();
        this.productPrice = event.getProductPrice();
        this.productStock = event.getProductStock();
        this.imageUrl = event.getImageUrl();
    }

    @CommandHandler
    public void handle(UpdateProductPriceCommand command) {
        var event = ProductPriceUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .basketProductId(command.getId())
                .productId(command.getProductId())
                .newPrice(command.getNewPrice())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ProductPriceUpdatedEvent event) {
        this.productPrice = event.getNewPrice();
    }

    @CommandHandler
    public void handle(UpdateProductStockCommand command) {
        var event = ProductStockUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .basketProductId(command.getId())
                .productId(command.getProductId())
                .newStock(command.getNewStock())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ProductStockUpdatedEvent event) {
        this.id = event.getId();
        this.productId = event.getId();
        this.productStock = event.getNewStock();
    }


}
