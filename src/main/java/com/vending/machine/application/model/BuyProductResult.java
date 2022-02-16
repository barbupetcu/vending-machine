package com.vending.machine.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyProductResult {
    private Integer totalSpent;
    private String productName;
    private Integer rest;
}
