package com.vending.machine.domain.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
public class Product extends BaseEntity {
    private BigDecimal amountAvailable;
    private BigDecimal cost;
    private String name;
    private String sellerId;
}
