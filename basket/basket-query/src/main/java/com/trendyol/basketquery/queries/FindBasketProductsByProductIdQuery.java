package com.trendyol.basketquery.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindBasketProductsByProductIdQuery {
    private String productId;
}
