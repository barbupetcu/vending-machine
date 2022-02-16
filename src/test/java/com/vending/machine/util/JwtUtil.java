package com.vending.machine.util;

import com.vending.machine.application.model.CustomUserDetail;
import com.vending.machine.domain.model.RoleType;
import com.vending.machine.infrastructure.security.SecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.vending.machine.application.service.AuthenticationService.ROLE_CLAIM;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final SecurityProperties securityProperties;
    private final JwtEncoder jwtEncoder;

    public String generateToken(Long userId, List<RoleType> roles) {
        Instant now = Instant.now();

        String role = roles.stream()
                .map(RoleType::name)
                .collect(Collectors.joining(","));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .id(String.valueOf(userId))
                .issuer(securityProperties.getIssuer())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(securityProperties.getExpiresInSeconds()))
                .subject("test")
                .claim(ROLE_CLAIM, role)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
