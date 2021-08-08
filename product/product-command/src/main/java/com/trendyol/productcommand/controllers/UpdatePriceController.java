package com.trendyol.productcommand.controllers;

import com.trendyol.productcommand.commands.UpdatePriceCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/updatePrice")
public class UpdatePriceController {
    private final CommandGateway commandGateway;

    @Autowired
    public UpdatePriceController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<String> updateProductPrice(@PathVariable(value="id") String id,
                                                     @RequestBody UpdatePriceCommand command) {

        try {
            command.setId(id);
            commandGateway.send(command);

            return new ResponseEntity<>("Price updated!", HttpStatus.OK);

        } catch (Exception e ) {
            System.out.println(e.toString());
            return new ResponseEntity<>("Could not update price", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
