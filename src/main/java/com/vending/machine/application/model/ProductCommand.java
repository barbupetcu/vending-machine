package com.vending.machine.application.model;

import lombok.Getter;
import org.springframework.security.core.Authentication;

@Getter
public class ProductCommand extends UserRequest {

    private final Long id;
    private final Integer cost;
    private final Integer amountAvailable;
    private final String name;

    ProductCommand(Authentication authentication, Long id, Integer cost, Integer amountAvailable, String name) {
        super(UserRequest.getUserId(authentication));
        this.id = id;
        this.cost = cost;
        this.amountAvailable = amountAvailable;
        this.name = name;
    }
}
