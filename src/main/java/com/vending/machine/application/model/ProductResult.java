package com.vending.machine.application.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResult {
    private Long id;
    private Integer amountAvailable;
    private Integer cost;
    private String name;
    private Long sellerId;
}
