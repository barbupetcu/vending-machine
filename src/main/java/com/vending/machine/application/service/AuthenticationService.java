package com.vending.machine.application.service;

import com.vending.machine.application.exception.UserNotFoundException;
import com.vending.machine.application.model.CustomUserDetail;
import com.vending.machine.domain.model.RoleType;
import com.vending.machine.domain.model.User;
import com.vending.machine.domain.model.UserRole;
import com.vending.machine.domain.repository.UserRepository;
import com.vending.machine.infrastructure.security.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    public static final String ROLE_CLAIM = "ROLE";

    private final JwtEncoder encoder;
    private final SecurityProperties securityProperties;
    private final UserRepository userRepository;

    public String refreshToken(Long userId) {
        Instant now = Instant.now();
        User user = userRepository.findByIdWithRoles(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        String role = user.getRoles()
                .stream()
                .map(UserRole::getRole)
                .map(RoleType::name)
                .collect(Collectors.joining(","));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .id(String.valueOf(user.getId()))
                .issuer(securityProperties.getIssuer())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(securityProperties.getExpiresInSeconds()))
                .subject(user.getUsername())
                .claim(ROLE_CLAIM, role)
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String getToken(Authentication authentication) {
        Instant now = Instant.now();
        if (!(authentication.getPrincipal() instanceof CustomUserDetail)) {
            throw new SecurityException("Principal doesn't meet required type");
        }
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .id(String.valueOf(userDetail.getUserId()))
                .issuer(securityProperties.getIssuer())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(securityProperties.getExpiresInSeconds()))
                .subject(authentication.getName())
                .claim(ROLE_CLAIM, role)
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}