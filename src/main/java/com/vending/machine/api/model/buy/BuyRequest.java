package com.vending.machine.api.model.buy;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class BuyRequest {
    @NotNull(message = "ProductId is required")
    @Positive(message = "ProductId should be greater than 0")
    private Long productId;
    @NotNull(message = "Amount of products is required")
    @Positive(message = "Amount of products should be greater than 0")
    private Integer amountOfProducts;
}
