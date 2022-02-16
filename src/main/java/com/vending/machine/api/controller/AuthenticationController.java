package com.vending.machine.api.controller;

import com.vending.machine.api.model.AuthenticationResponse;
import com.vending.machine.application.service.AuthenticationService;
import com.vending.machine.infrastructure.OpenApiConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout/all";

    private final AuthenticationService authenticationService;

    @Operation(description = "Login endpoint", security = @SecurityRequirement(name = OpenApiConfiguration.AUTHORIZATION_BASIC))
    @PostMapping(LOGIN)
    public AuthenticationResponse login(Authentication authentication) {
        String jwtToken = authenticationService.getToken(authentication);
        return AuthenticationResponse.withToken(jwtToken);
    }

    @Operation(description = "Logout endpoint")
    @PostMapping(LOGOUT)
    public void logout() {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }
}
