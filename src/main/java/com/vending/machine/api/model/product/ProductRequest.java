package com.vending.machine.api.model.product;

import com.vending.machine.api.model.CustomJsonSerializable;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ProductRequest implements CustomJsonSerializable {
    @NotBlank(message = "Name must be not null")
    private String name;
    @NotNull(message = "Cost must be not null")
    @Positive(message = "Cost must be greated than 0")
    private Integer cost;
    @NotNull(message = "amount available must be not null")
    @Min(value = 0, message = "amount available most not be lower than 0")
    private Integer amountAvailable;
}
