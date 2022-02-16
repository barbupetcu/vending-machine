package com.vending.machine.application.exception;

import org.springframework.security.core.AuthenticationException;

public class SecurityException extends AuthenticationException {

    public SecurityException(String msg) {
        super(msg);
    }
}
