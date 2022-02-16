package com.vending.machine.application.model;

import lombok.Getter;
import org.springframework.security.core.Authentication;

@Getter
public class BuyProductCommand extends UserRequest {

    private final Long productId;
    private final Integer amountOfProducts;

    public BuyProductCommand(Authentication authentication, Long productId, Integer amountOfProducts) {
        super(UserRequest.getUserId(authentication));
        this.productId = productId;
        this.amountOfProducts = amountOfProducts;
    }
}
