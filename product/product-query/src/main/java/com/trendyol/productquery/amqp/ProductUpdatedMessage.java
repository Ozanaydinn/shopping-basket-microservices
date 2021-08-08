package com.trendyol.productquery.amqp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trendyol.productquery.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdatedMessage implements Serializable {
    @JsonProperty("id")
    private String id;

    @JsonProperty("updateType")
    private String updateType;

    @JsonProperty("product")
    private ProductDTO product;
}
