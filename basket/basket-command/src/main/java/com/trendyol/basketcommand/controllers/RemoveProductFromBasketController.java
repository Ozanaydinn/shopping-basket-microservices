package com.trendyol.basketcommand.controllers;

import com.trendyol.basketcommand.commands.RemoveProductFromBasketCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/removeBasketProduct")
public class RemoveProductFromBasketController {
    private final CommandGateway commandGateway;

    @Autowired
    public RemoveProductFromBasketController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }
}