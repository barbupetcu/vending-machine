package com.vending.machine.api.model.product;

import com.vending.machine.application.util.JsonUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductRequestTestBuilder {

    public String productRequestJson(Integer cost, Integer amountAvailable) {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setCost(cost);
        productRequest.setAmountAvailable(amountAvailable);
        productRequest.setName("name");
        return JsonUtil.toString(productRequest);
    }

}