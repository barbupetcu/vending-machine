package com.vending.machine.application.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BuyProductResult {
    private Integer totalSpent;
    private String productName;
    private Integer rest;
}
