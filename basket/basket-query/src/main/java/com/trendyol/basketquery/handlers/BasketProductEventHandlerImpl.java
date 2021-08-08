package com.trendyol.basketquery.handlers;

import com.trendyol.basketcore.dto.BasketProductLookupResponse;
import com.trendyol.basketcore.dto.NotificationDTO;
import com.trendyol.basketcore.events.ProductAddedToBasketEvent;
import com.trendyol.basketcore.events.ProductPriceUpdatedEvent;
import com.trendyol.basketcore.events.ProductStockUpdatedEvent;
import com.trendyol.basketcore.models.BasketProducts;
import com.trendyol.basketcore.models.NotificationType;
import com.trendyol.basketquery.amqp.CreateNotificationMessage;
import com.trendyol.basketquery.amqp.MessageProducer;
import com.trendyol.basketquery.queries.FindBasketProductByIdQuery;
import com.trendyol.basketquery.queries.FindBasketProductsByProductIdQuery;
import com.trendyol.basketquery.repositories.BasketProductRepository;
import com.trendyol.basketquery.repositories.BasketRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@ProcessingGroup("basket-product-group")
public class BasketProductEventHandlerImpl implements BasketProductEventHandler{

    private final BasketProductRepository basketProductRepository;
    private final BasketRepository basketRepository;
    private final QueryGateway queryGateway;
    private final MessageProducer messageProducer;

    @Autowired
    public BasketProductEventHandlerImpl(BasketProductRepository basketProductRepository, QueryGateway queryGateway, MessageProducer messageProducer, BasketRepository basketRepository) {
        this.basketProductRepository = basketProductRepository;
        this.queryGateway = queryGateway;
        this.messageProducer = messageProducer;
        this.basketRepository = basketRepository;
    }
    @EventHandler
    @Override
    public void on(ProductAddedToBasketEvent event) {
        // first check if product is already in the basket
        var query = new FindBasketProductsByProductIdQuery(event.getProductId());

        var response = queryGateway.query(query, ResponseTypes.instanceOf(BasketProductLookupResponse.class)).join();

        if (response.getBasketProducts() != null) {
            for (BasketProducts basketProduct : response.getBasketProducts()) {
                if (basketProduct.getBasketId().equals(event.getBasketId())) {
                    var newAmount = basketProduct.getAmount() + 1;
                    basketProduct.setAmount(newAmount);
                    basketProductRepository.save(basketProduct);
                }
            }
        }
        else {
            var basketProduct = BasketProducts.builder()
                    .id(event.getId())
                    .basketId(event.getBasketId())
                    .productId(event.getProductId())
                    .productTitle(event.getProductTitle())
                    .productPrice(event.getProductPrice())
                    .productStock(event.getProductStock())
                    .amount(1)
                    .build();
            basketProductRepository.save(basketProduct);
        }
    }

    @EventHandler
    @Override
    public void on(ProductPriceUpdatedEvent event) {
        var query = new FindBasketProductByIdQuery(event.getBasketProductId());
        var response = queryGateway.query(query,ResponseTypes.instanceOf(BasketProductLookupResponse.class)).join();

        if (response.getBasketProducts() != null || response.getBasketProducts().size() > 0) {
            var basketProduct = response.getBasketProducts().get(0);
            var oldPrice = basketProduct.getProductPrice();
            basketProduct.setProductPrice(event.getNewPrice());

            var newBasketProduct = basketProductRepository.save(basketProduct);
            System.out.println("Price updated in the database ");

            if (newBasketProduct.getProductPrice() < oldPrice) {
                System.out.println("Price is reduced, sending notification request.");

                // first, get the userId for the corresponding basketId
                var basket = basketRepository.findById(newBasketProduct.getBasketId());


                // Create a notification DTO
                var notificationDTO = NotificationDTO.builder()
                        .id(newBasketProduct.getId())
                        .userId(basket.get().getUserId())
                        .type(NotificationType.PRICE_REDUCED)
                        .productId(newBasketProduct.getProductId())
                        .build();

                // Create a message
                var notificationMessage  = CreateNotificationMessage.builder()
                        .id(UUID.randomUUID().toString())
                        .notification(notificationDTO)
                        .build();

                messageProducer.sendCreateNotificationMessage(notificationMessage);

            }
        }
    }
    @EventHandler
    @Override
    public void on(ProductStockUpdatedEvent event) {
        var query = new FindBasketProductByIdQuery(event.getBasketProductId());
        var response = queryGateway.query(query,ResponseTypes.instanceOf(BasketProductLookupResponse.class)).join();

        if (response.getBasketProducts() != null || response.getBasketProducts().size() > 0) {
            var basketProduct = response.getBasketProducts().get(0);
            var oldStock = basketProduct.getProductStock();
            basketProduct.setProductStock(event.getNewStock());

            var newBasketProduct = basketProductRepository.save(basketProduct);
            System.out.println("Stock updated in the database ");

            if (newBasketProduct.getProductStock() < 3 && newBasketProduct.getProductStock() != 0) {
                System.out.println("Stock is less than 3, sending notification request.");

                // first, get the userId for the corresponding basketId
                var basket = basketRepository.findById(newBasketProduct.getBasketId());


                // Create a notification DTO
                var notificationDTO = NotificationDTO.builder()
                        .id(newBasketProduct.getId())
                        .userId(basket.get().getUserId())
                        .type(NotificationType.STOCK_LESS_THAN_THREE)
                        .productId(newBasketProduct.getProductId())
                        .build();

                // Create a message
                var notificationMessage  = CreateNotificationMessage.builder()
                        .id(UUID.randomUUID().toString())
                        .notification(notificationDTO)
                        .build();

                messageProducer.sendCreateNotificationMessage(notificationMessage);

            }
            if (newBasketProduct.getProductStock() == 0) {
                // first, get the userId for the corresponding basketId
                var basket = basketRepository.findById(newBasketProduct.getBasketId());


                // Create a notification DTO
                var notificationDTO = NotificationDTO.builder()
                        .id(newBasketProduct.getId())
                        .userId(basket.get().getUserId())
                        .type(NotificationType.OUT_OF_STOCK)
                        .productId(newBasketProduct.getProductId())
                        .build();

                // Create a message
                var notificationMessage  = CreateNotificationMessage.builder()
                        .id(UUID.randomUUID().toString())
                        .notification(notificationDTO)
                        .build();

                messageProducer.sendCreateNotificationMessage(notificationMessage);
            }
        }
    }
}
