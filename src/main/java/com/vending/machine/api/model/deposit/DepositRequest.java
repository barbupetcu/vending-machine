package com.vending.machine.api.model.deposit;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepositRequest {
    @NotNull
    private Integer coin;
}
