package com.springbank.user.query.api.handlers;

import com.springbank.user.core.events.UserRegisteredEvent;
import com.springbank.user.core.events.UserRemovedEvent;
import com.springbank.user.core.events.UserUpdatedEvent;
import com.springbank.user.query.api.amqp.MessageProducer;
import com.springbank.user.query.api.amqp.UserCreatedMessage;
import com.springbank.user.query.api.dto.UserDTO;
import com.springbank.user.query.api.repositories.UserRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@ProcessingGroup("user-group")
public class UserEventHandlerImpl implements UserEventHandler {

    private final UserRepository userRepository;
    private final MessageProducer messageProducer;
    @Autowired
    public UserEventHandlerImpl(UserRepository userRepository, MessageProducer messageProducer) {
        this.userRepository = userRepository;
        this.messageProducer = messageProducer;
    }

    @EventHandler
    @Override
    public void on(UserRegisteredEvent event) {
        var user = userRepository.save(event.getUser());

        var userDTO = UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .emailAddress(user.getEmailAddress())
                .basketId(user.getBasketId())
                .build();
        // now send a user-message to the Basket service
        var userCreatedMessage = UserCreatedMessage.builder()
                .id(UUID.randomUUID().toString())
                .user(userDTO)
                .build();
        System.out.println(userCreatedMessage);
        messageProducer.sendUserCreatedMessage(userCreatedMessage);
    }

    @EventHandler
    @Override
    public void on(UserUpdatedEvent event) {
        userRepository.save(event.getUser());
    }

    @EventHandler
    @Override
    public void on(UserRemovedEvent event) {
        userRepository.deleteById(event.getId());
    }
}
