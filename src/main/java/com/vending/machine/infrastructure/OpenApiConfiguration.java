package com.vending.machine.infrastructure;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    public static final String AUTHORIZATION_BEARER = "AUTHORIZATION_HEADER";
    public static final String AUTHORIZATION_BASIC = "AUTHORIZATION_BASIC";

    @Bean
    public OpenAPI authorizationHeader() {
        return new OpenAPI().components(
                new Components()
                        .addSecuritySchemes(
                                AUTHORIZATION_BEARER,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization")
                        )
                        .addSecuritySchemes(
                                AUTHORIZATION_BASIC,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")
                                        .name("Authorization")
                        )
        );
    }
}
