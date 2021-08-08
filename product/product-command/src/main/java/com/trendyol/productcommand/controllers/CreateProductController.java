package com.trendyol.productcommand.controllers;

import com.trendyol.productcommand.commands.CreateProductCommand;
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
@RequestMapping(path = "/api/createProduct")
public class CreateProductController {
    private final CommandGateway commandGateway;

    @Autowired
    public CreateProductController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductCommand command) {
        var id = UUID.randomUUID().toString();
        System.out.println("ID is: " + id);
        command.setId(id);

        try {
            commandGateway.send(command);
            return new ResponseEntity<>("Product created! ", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());

            return new ResponseEntity<>("Couldn't create product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
