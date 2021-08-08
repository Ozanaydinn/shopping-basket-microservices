package com.trendyol.basketcommand.controllers;

import com.trendyol.basketcommand.commands.UpdateProductPriceCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/updateBasketProductPrice")
public class UpdateProductPriceController {
    private final CommandGateway commandGateway;

    @Autowired
    public UpdateProductPriceController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping
    public ResponseEntity<String> updateProductPrice(@RequestBody UpdateProductPriceCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);

        try {
            commandGateway.send(command);

            return new ResponseEntity<>("Price updated!", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());

            return new ResponseEntity<>("Could not update price!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
