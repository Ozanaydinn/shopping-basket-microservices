package com.trendyol.basketquery.handlers;

import com.trendyol.basketcore.dto.BasketProductLookupResponse;
import com.trendyol.basketquery.queries.FindBasketProductByIdQuery;
import com.trendyol.basketquery.queries.FindBasketProductsByBasketIdQuery;
import com.trendyol.basketquery.queries.FindBasketProductsByProductIdQuery;
import com.trendyol.basketquery.queries.GetAllBasketProductsQuery;

public interface BasketProductQueryHandler {
    BasketProductLookupResponse findBasketProductById(FindBasketProductByIdQuery query);
    BasketProductLookupResponse findBasketProductsByProductId(FindBasketProductsByProductIdQuery query);
    BasketProductLookupResponse findBasketProductsByBasketId(FindBasketProductsByBasketIdQuery query);
    BasketProductLookupResponse getAllBasketProducts(GetAllBasketProductsQuery query);
}
