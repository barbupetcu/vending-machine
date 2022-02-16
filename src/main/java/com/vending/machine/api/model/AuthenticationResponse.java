package com.vending.machine.api.model;

import com.vending.machine.infrastructure.commons.CustomJsonSerializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AuthenticationResponse implements CustomJsonSerializable {
    private String token;
    private String errorMessage;
}