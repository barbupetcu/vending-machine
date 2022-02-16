package com.vending.machine.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "withToken")
@Getter
public class AuthenticationResponse{
    private String token;
}