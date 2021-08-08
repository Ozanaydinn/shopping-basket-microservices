package com.trendyol.basketquery.handlers;

import com.trendyol.basketcore.dto.BasketProductLookupResponse;
import com.trendyol.basketcore.models.BasketProducts;
import com.trendyol.basketquery.queries.FindBasketProductByIdQuery;
import com.trendyol.basketquery.queries.FindBasketProductsByBasketIdQuery;
import com.trendyol.basketquery.queries.FindBasketProductsByProductIdQuery;
import com.trendyol.basketquery.queries.GetAllBasketProductsQuery;
import com.trendyol.basketquery.repositories.BasketProductRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BasketProductQueryHandlerImpl implements BasketProductQueryHandler{
    private final BasketProductRepository basketProductRepository;

    @Autowired
    public BasketProductQueryHandlerImpl(BasketProductRepository basketProductRepository) {
        this.basketProductRepository = basketProductRepository;
    }

    @Override
    @QueryHandler
    public BasketProductLookupResponse getAllBasketProducts(GetAllBasketProductsQuery query) {
        var basketProductIterator = basketProductRepository.findAll();

        if (!basketProductIterator.iterator().hasNext()) {
            return new BasketProductLookupResponse("No Basket Products were Found!");
        }
        var basketProducts = new ArrayList<BasketProducts>();
        basketProductIterator.forEach(i -> basketProducts.add(i));

        return new BasketProductLookupResponse(basketProducts, "Successful!");
    }

    @Override
    @QueryHandler
    public BasketProductLookupResponse findBasketProductById(FindBasketProductByIdQuery query) {
        System.out.println(query.getId());
        var basketProduct = basketProductRepository.findById(query.getId());
        System.out.println(basketProduct);
         return basketProduct.isPresent()
                ? new BasketProductLookupResponse(basketProduct.get(), "Basket product returned!")
                : new BasketProductLookupResponse("No basket product with id: " + query.getId());


    }
    // BURDA HEPSINI ÇEKİP KENDIN DE BAKABILIRSIN
    @QueryHandler
    @Override
    public BasketProductLookupResponse findBasketProductsByProductId(FindBasketProductsByProductIdQuery query) {
        var basketProducts = basketProductRepository.findBasketProductsByProductId(query.getProductId());

        return basketProducts != null && basketProducts.size() > 0
                ? new BasketProductLookupResponse(basketProducts, "Successfully returned!")
                : new BasketProductLookupResponse("No basket products were found");
    }

    @QueryHandler
    @Override
    public BasketProductLookupResponse findBasketProductsByBasketId(FindBasketProductsByBasketIdQuery query) {
        var basketProducts = basketProductRepository.findBasketProductsByBasketId(query.getBasketId());

        return basketProducts != null && basketProducts.size() > 0
                ? new BasketProductLookupResponse(basketProducts, "Successfully returned!")
                : new BasketProductLookupResponse("No basket products were found");
    }
}
