package com.trendyol.productquery.handlers;

import com.trendyol.productcore.events.PriceUpdatedEvent;
import com.trendyol.productcore.events.ProductCreatedEvent;
import com.trendyol.productcore.events.StockUpdatedEvent;
import com.trendyol.productquery.amqp.MessageProducer;
import com.trendyol.productquery.amqp.ProductUpdatedMessage;
import com.trendyol.productquery.dto.ProductDTO;
import com.trendyol.productquery.repositories.ProductRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@ProcessingGroup("product-group")
public class ProductEventHandlerImpl implements ProductEventHandler{
    private final ProductRepository productRepository;
    private final MessageProducer messageProducer;

    @Autowired
    public ProductEventHandlerImpl(ProductRepository productRepository, MessageProducer messageProducer){
        this.productRepository = productRepository;
        this.messageProducer = messageProducer;
    }

    @EventHandler
    @Override
    public void on(ProductCreatedEvent event) {
        var product = event.getProduct();
        product.setId(event.getId());
        productRepository.save(product);
    }

    @EventHandler
    @Override
    public void on(PriceUpdatedEvent event) {
        var product = productRepository.findById(event.getId());

        if (product.isEmpty()){
            return;
        }
        else {
            product.get().setPrice(event.getNewPrice());
            var newProduct = productRepository.save(product.get());

            // now send a message to the Basket service to let them know a price has changed
            var productDTO = ProductDTO.builder()
                    .id(newProduct.getId())
                    .title(newProduct.getTitle())
                    .price(newProduct.getPrice())
                    .stock(newProduct.getStock())
                    .imageUrl(newProduct.getImageUrl())
                    .build();
            var priceUpdatedMessage = ProductUpdatedMessage.builder()
                    .id(UUID.randomUUID().toString())
                    .updateType("price")
                    .product(productDTO)
                    .build();
            System.out.println(priceUpdatedMessage);
            messageProducer.sendProductUpdatedMessage(priceUpdatedMessage);
        }
    }

    @EventHandler
    @Override
    public void on(StockUpdatedEvent event) {
        var product = productRepository.findById(event.getId());

        if (product.isEmpty()) return;

        product.get().setStock(event.getNewStock());
        var newProduct = productRepository.save(product.get());

        // now send a message to the Basket service to let them know a stock has changed
        var productDTO = ProductDTO.builder()
                .id(newProduct.getId())
                .title(newProduct.getTitle())
                .price(newProduct.getPrice())
                .stock(newProduct.getStock())
                .imageUrl(newProduct.getImageUrl())
                .build();
        var stockUpdatedMessage = ProductUpdatedMessage.builder()
                .id(UUID.randomUUID().toString())
                .updateType("stock")
                .product(productDTO)
                .build();
        messageProducer.sendProductUpdatedMessage(stockUpdatedMessage);
    }
}
