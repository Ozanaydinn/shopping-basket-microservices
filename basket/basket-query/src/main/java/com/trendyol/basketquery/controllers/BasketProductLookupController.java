package com.trendyol.basketquery.controllers;

import com.trendyol.basketcore.dto.BasketProductLookupResponse;
import com.trendyol.basketquery.queries.FindBasketProductByIdQuery;
import com.trendyol.basketquery.queries.FindBasketProductsByBasketIdQuery;
import com.trendyol.basketquery.queries.FindBasketProductsByProductIdQuery;
import com.trendyol.basketquery.queries.GetAllBasketProductsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/basketProductLookup")
public class BasketProductLookupController {
    private final QueryGateway queryGateway;

    @Autowired
    public BasketProductLookupController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping(path = "/")
    public ResponseEntity<BasketProductLookupResponse> getAllAccounts() {
        try {
            var query = new GetAllBasketProductsQuery();
            var response = queryGateway.query(query, ResponseTypes.instanceOf(BasketProductLookupResponse.class)).join();

            if (response == null || response.getBasketProducts() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get all product request";
            System.out.println(e.toString());

            return new ResponseEntity<>(new BasketProductLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path="/byId/{id}")
    public ResponseEntity<BasketProductLookupResponse> getBasketProductById(@PathVariable(value = "id") String id) {
        try {
            System.out.println("INSIDE METHOD");
            var query = new FindBasketProductByIdQuery(id);

            var response  = queryGateway.query(query, ResponseTypes.instanceOf(BasketProductLookupResponse.class)).join();
            System.out.println(response.getBasketProducts());
            if (response == null || response.getBasketProducts() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(new BasketProductLookupResponse("Can't get product with id " + id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byProductId/{productId}")
    public ResponseEntity<BasketProductLookupResponse> getBasketProductsByUserId(@PathVariable(value = "productId") String productId) {
        try {
            var query = new FindBasketProductsByProductIdQuery(productId);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(BasketProductLookupResponse.class)).join();

            if (response == null || response.getBasketProducts() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch(Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(new BasketProductLookupResponse("No product found with product id: " + productId), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byBasketId/{basketId}")
    public ResponseEntity<BasketProductLookupResponse> getBasketProductsBasketId(@PathVariable(value = "basketId") String basketId) {
        try {
            var query = new FindBasketProductsByBasketIdQuery(basketId);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(BasketProductLookupResponse.class)).join();

            if (response == null || response.getBasketProducts() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch(Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(new BasketProductLookupResponse("No product found with basket id: " + basketId), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
