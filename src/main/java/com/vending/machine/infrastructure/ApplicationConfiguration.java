package com.vending.machine.infrastructure;

import com.vending.machine.infrastructure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value = {SecurityProperties.class})
public class ApplicationConfiguration {
}