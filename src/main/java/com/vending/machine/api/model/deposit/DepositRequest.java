package com.vending.machine.api.model.deposit;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class DepositRequest {
    @NotNull(message = "coin is required")
    @Positive(message = "coin should be greater than 0")
    private Integer coin;
}
