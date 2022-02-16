package com.vending.machine.application.service;

import com.vending.machine.infrastructure.security.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtEncoder encoder;
    private final SecurityProperties securityProperties;

    public String getToken(Authentication authentication) {
        Instant now = Instant.now();
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(securityProperties.getIssuer())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(securityProperties.getExpiresInSeconds()))
                .subject(authentication.getName())
                .claim("role", role)
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}