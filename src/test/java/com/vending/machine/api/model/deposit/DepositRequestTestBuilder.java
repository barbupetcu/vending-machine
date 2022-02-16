package com.vending.machine.api.model.deposit;

import com.vending.machine.application.util.JsonUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DepositRequestTestBuilder {

    public String depositRequest(Integer coin) {
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setCoin(coin);
        return JsonUtil.toString(depositRequest);
    }

}