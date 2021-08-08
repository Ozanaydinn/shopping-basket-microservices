package com.trendyol.basketcommand.controllers;

import com.trendyol.basketcommand.commands.CreateBasketCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/createBasket")
public class CreateBasketController {
    private final CommandGateway commandGateway;

    @Autowired
    public CreateBasketController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<String> createBasket(@RequestBody CreateBasketCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandGateway.send(command);

            return new ResponseEntity<>("Basket successfully created!", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());

            return new ResponseEntity<>("Basket could not be created!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
