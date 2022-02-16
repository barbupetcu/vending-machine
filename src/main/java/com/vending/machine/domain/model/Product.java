package com.vending.machine.domain.model;

import com.vending.machine.application.model.BuyProductCommand;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    private Integer amountAvailable;
    private Integer cost;
    private String name;
    private String sellerId;

    public Product buyProduct(BuyProductCommand buyProductCommand) {
        this.amountAvailable -= buyProductCommand.getAmountOfProducts();
        return this;
    }
}
