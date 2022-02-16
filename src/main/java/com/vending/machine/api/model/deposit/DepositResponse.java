package com.vending.machine.api.model.deposit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "withTotalAmount")
@NoArgsConstructor
@Getter
public class DepositResponse {
    private Integer total;
}
