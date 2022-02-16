package com.vending.machine.application.model;

import com.vending.machine.application.exception.SecurityException;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequest {
    private final Long userId;

    public static UserRequest build(Authentication user) {
        return new UserRequest(getUserId(user));
    }

    protected static Long getUserId(Authentication authentication) {
        if (!(authentication instanceof JwtAuthenticationToken)) {
            throw new SecurityException("Authentication method not allowed");
        }
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        try {
            return Long.valueOf(jwtAuthenticationToken.getToken().getId());
        } catch (NumberFormatException e) {
            throw new SecurityException("UserId is not accepted");
        }
    }
}
