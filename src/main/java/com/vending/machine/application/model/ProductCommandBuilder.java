package com.vending.machine.application.model;

import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;

@NoArgsConstructor(staticName = "builder")
public class ProductCommandBuilder {
    private Authentication authentication;
    private Long productId;
    private Integer cost;
    private Integer amountAvailable;
    private String name;

    public ProductCommandBuilder setAuthentication(Authentication authentication) {
        this.authentication = authentication;
        return this;
    }

    public ProductCommandBuilder setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public ProductCommandBuilder setCost(Integer cost) {
        this.cost = cost;
        return this;
    }

    public ProductCommandBuilder setAmountAvailable(Integer amountAvailable) {
        this.amountAvailable = amountAvailable;
        return this;
    }

    public ProductCommandBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductCommand createProductCommand() {
        return new ProductCommand(authentication, productId, cost, amountAvailable, name);
    }
}