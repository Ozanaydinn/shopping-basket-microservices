package com.trendyol.basketcommand.amqp;


import com.trendyol.basketcommand.commands.CreateBasketCommand;
import com.trendyol.basketcommand.commands.UpdateProductPriceCommand;
import com.trendyol.basketcommand.commands.UpdateProductStockCommand;
import com.trendyol.basketcore.dto.BasketProductLookupResponse;
import com.trendyol.basketcore.dto.UserDTO;
import com.trendyol.basketcore.models.Basket;
import com.trendyol.basketcore.models.BasketProducts;
import jdk.swing.interop.SwingInterOpUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MessageConsumer {
    Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    private final CommandGateway commandGateway;
    private final WebClient webClient;

    @Autowired
    public MessageConsumer(CommandGateway commandGateway) {
        System.out.println("inside constructor");
        this.commandGateway = commandGateway;
        this.webClient = WebClient.create();
    }

    @RabbitListener(queues = "${rabbit.queues.user-basket-command-queue}")
    public void handleUserCommandQueueMessages(UserCreatedMessage message) {
        // create a new basket object
        Basket basket = Basket.builder()
                .id(message.getUser().getBasketId())
                .userId(message.getUser().getId())
                .build();
        // Create a command to build the basket
        CreateBasketCommand command = CreateBasketCommand.builder()
                .id(basket.getId())
                .basket(basket)
                .build();
        commandGateway.send(command);
    }
    @RabbitListener(queues = "${rabbit.queues.product-basket-command-queue}")
    public void handleProductCommandQueueMessages(ProductUpdatedMessage message) {
        if (message.getUpdateType().equals("price")) {
            System.out.println("PRICE UPDATE");
            // first send a request to get all the basket products with given product id
            BasketProductLookupResponse response = webClient.get()
                    .uri("localhost:7072/api/basketProductLookup/byProductId/" + message.getProduct().getId())
                    .retrieve()
                    .bodyToMono(BasketProductLookupResponse.class).block();

            System.out.println(response);

            if (response.getBasketProducts() != null || response.getBasketProducts().size() > 0) {
                System.out.println("INSIDE IF");
                for (BasketProducts basketProduct : response.getBasketProducts()) {
                    // create an update command for each basket product with a given id
                    UpdateProductPriceCommand command = UpdateProductPriceCommand.builder()
                            .id(basketProduct.getId())
                            .productId(basketProduct.getProductId())
                            .newPrice(message.getProduct().getPrice())
                            .build();
                    System.out.println(command);
                    commandGateway.send(command);
                }
            }

        }
        else {
            System.out.println("STOCK UPDATED");
            BasketProductLookupResponse response = webClient.get()
                    .uri("localhost:7072/api/basketProductLookup/byProductId/" + message.getProduct().getId())
                    .retrieve()
                    .bodyToMono(BasketProductLookupResponse.class).block();

            System.out.println(response);
            if (response.getBasketProducts() != null || response.getBasketProducts().size() > 0) {
                for (BasketProducts basketProduct : response.getBasketProducts()) {
                    // create an update command for each basket product with a given id
                    UpdateProductStockCommand command = UpdateProductStockCommand.builder()
                            .id(basketProduct.getId())
                            .productId(basketProduct.getProductId())
                            .newStock(message.getProduct().getStock())
                            .build();
                    System.out.println(command);
                    commandGateway.send(command);
                }
            }
        }
    }
    /*
    @RabbitListener(queues = "${rabbit.queues.user-basket-query-queue}")
    public void handleQueryQueueMessages(UserCreatedMessage message) {
        System.out.println(message.toString());
    }
     */
}
