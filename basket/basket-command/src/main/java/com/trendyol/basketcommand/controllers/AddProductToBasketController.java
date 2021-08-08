package com.trendyol.basketcommand.controllers;


import com.trendyol.basketcommand.commands.AddProductToBasketCommand;
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
@RequestMapping(path = "/api/basketProduct")
public class AddProductToBasketController {
    private final CommandGateway commandGateway;

    @Autowired
    public AddProductToBasketController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<String> addProductToBasket(@RequestBody AddProductToBasketCommand command) {
        var id = UUID.randomUUID().toString();
        System.out.println("ID " + id);
        command.setId(id);

        try {
            commandGateway.send(command);

            return new ResponseEntity<>("Product successfully added to basket!", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());

            return new ResponseEntity<>("Could not add product to basket", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
