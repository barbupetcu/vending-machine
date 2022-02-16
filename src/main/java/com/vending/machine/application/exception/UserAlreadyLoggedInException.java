package com.vending.machine.application.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyLoggedInException extends AuthenticationException {

    private static final String MESSAGE = "There is already an active session using your account";

    public UserAlreadyLoggedInException() {
        super(MESSAGE);
    }
}
