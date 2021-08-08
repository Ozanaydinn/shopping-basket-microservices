package com.springbank.user.query.api.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    @Value("${rabbit.queues.user-basket-command-queue}")
    private String commandQueue;

    @Value("${rabbit.queues.user-basket-query-queue}")
    private String queryQueue;

    @Value("${rabbit.exchanges.user-basket-exchange}")
    private String exchange;

    @Value("${rabbit.keys.user-basket-command-key}")
    private String commandKey;

    @Value("${rabbit.keys.user-basket-query-key}")
    private String queryKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendUserCreatedMessage(UserCreatedMessage message) {
        logger.trace("User communication info sent with user id: {}", message.getUser().getId());
        rabbitTemplate.convertAndSend(exchange, commandKey, message);
    }

    public void sendUserNotificationMessage(UserMailRequestMessage message) {
        rabbitTemplate.convertAndSend("user-notification", "userNotificationQueryKey", message );
    }
}
