package com.vending.machine.application.mapper;

import com.vending.machine.application.model.ProductCommand;
import com.vending.machine.application.model.ProductResult;
import com.vending.machine.domain.model.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {

    public ProductResult fromProduct(Product product) {
        return ProductResult.builder()
                .id(product.getId())
                .sellerId(product.getSellerId())
                .amountAvailable(product.getAmountAvailable())
                .cost(product.getCost())
                .name(product.getName())
                .build();
    }

    public Product fromCommand(ProductCommand productCommand) {
        Product product = Product.builder()
                .sellerId(productCommand.getUserId())
                .amountAvailable(productCommand.getAmountAvailable())
                .cost(productCommand.getCost())
                .name(productCommand.getName())
                .build();
        product.setId(productCommand.getId());
        return product;
    }
}
