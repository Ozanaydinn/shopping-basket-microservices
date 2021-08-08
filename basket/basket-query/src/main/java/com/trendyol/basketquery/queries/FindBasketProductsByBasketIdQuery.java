package com.trendyol.basketquery.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.AggregateIdentifier;

@Data
@AllArgsConstructor
public class FindBasketProductsByBasketIdQuery {
    private String basketId;
}
