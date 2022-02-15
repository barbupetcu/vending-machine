package com.vending.machine.application.exception;

public class UserAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "Username %s already exists";

    public UserAlreadyExistsException(String username) {
        super(String.format(MESSAGE, username));
    }
}
