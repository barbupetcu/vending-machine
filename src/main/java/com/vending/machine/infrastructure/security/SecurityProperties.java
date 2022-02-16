package com.vending.machine.infrastructure.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Getter
@Setter
@Component
@ConfigurationProperties("security.jwt")
public class SecurityProperties {

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
    private long expiresInSeconds;
    private String issuer;
}
