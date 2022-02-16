package com.vending.machine.api.model.buy;

import com.vending.machine.application.util.JsonUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BuyRequestTestBuilder {

    public String buyRequestJson(Long productId, Integer amountOfProducts) {
        BuyRequest buyRequest = new BuyRequest();
        buyRequest.setProductId(productId);
        buyRequest.setAmountOfProducts(amountOfProducts);
        return JsonUtil.toString(buyRequest);
    }
}