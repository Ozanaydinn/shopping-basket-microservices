package com.springbank.user.query.api.amqp;

import com.springbank.user.query.api.dto.UserLookupResponse;
import com.springbank.user.query.api.queries.FindUserByIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {
    private final MessageProducer messageProducer;
    private final QueryGateway queryGateway;

    @Autowired
    public MessageConsumer(MessageProducer messageProducer, QueryGateway queryGateway) {
        System.out.println("inside consumer");
        this.messageProducer = messageProducer;
        this.queryGateway = queryGateway;
    }

    @RabbitListener(queues = "${rabbit.queues.user-notification-command-queue}")
    public void handleNotificationCommandQueueMessages(UserMailRequestMessage message) {
        // get the corresponding user, using the user id
        System.out.println("Received a message, getting user email");
        var query = new FindUserByIdQuery(message.getNotification().getUserId());

        var result = queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)).join();
        System.out.println("response is: " + result.getUsers().toString());
        if (result.getUsers() != null) {
            // Create a new message
            var emailMessage = UserMailRequestMessage.builder()
                    .id(message.getId())
                    .email(result.getUsers().get(0).getEmailAddress())
                    .notification(message.getNotification())
                    .build();
            // send the email information back
            System.out.println("Sending back the email: " + emailMessage);
            messageProducer.sendUserNotificationMessage(emailMessage);
        }
    }
}
