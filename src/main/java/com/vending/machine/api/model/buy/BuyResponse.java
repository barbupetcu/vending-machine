package com.vending.machine.api.model.buy;

import com.vending.machine.application.model.Coin;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BuyResponse {
    private Integer totalSpent;
    private String productName;
    private List<Coin> rest;
}
