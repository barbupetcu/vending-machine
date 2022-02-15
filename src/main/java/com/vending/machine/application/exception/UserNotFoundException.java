package com.vending.machine.application.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Username %s doesn't exists";

    public UserNotFoundException(Long userId) {
        super(String.format(MESSAGE, userId));
    }
}
