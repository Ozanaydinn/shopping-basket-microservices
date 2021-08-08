package com.trendyol.productcommand.controllers;

import com.trendyol.productcommand.commands.UpdatePriceCommand;
import com.trendyol.productcommand.commands.UpdateStockCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/updateStock")
public class UpdateStockController {
    private final CommandGateway commandGateway;

    @Autowired
    public UpdateStockController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<String> updateProductStock(@PathVariable(value="id") String id,
                                                     @RequestBody UpdateStockCommand command) {

        try {
            command.setId(id);
            commandGateway.send(command);

            return new ResponseEntity<>("Stock updated!", HttpStatus.OK);

        } catch (Exception e ) {
            System.out.println(e.toString());
            return new ResponseEntity<>("Could not update stock", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
