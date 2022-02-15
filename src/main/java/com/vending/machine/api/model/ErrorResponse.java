package com.vending.machine.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "anErrorResponse")
public class ErrorResponse {

    private String errorMessage;
}