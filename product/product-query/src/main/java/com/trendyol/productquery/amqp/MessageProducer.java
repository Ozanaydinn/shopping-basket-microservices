package com.trendyol.productquery.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    @Value("${rabbit.queues.product-basket-command-queue}")
    private String commandQueue;

    @Value("${rabbit.queues.product-basket-query-queue}")
    private String queryQueue;

    @Value("${rabbit.exchanges.product-basket-exchange}")
    private String exchange;

    @Value("${rabbit.keys.product-basket-command-key}")
    private String commandKey;

    @Value("${rabbit.keys.product-basket-query-key}")
    private String queryKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendProductUpdatedMessage(ProductUpdatedMessage message) {
        rabbitTemplate.convertAndSend(exchange, commandKey, message);
    }

}
