package com.vending.machine.application.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "User %s doesn't exists";

    public UserNotFoundException(Long userId) {
        super(String.format(MESSAGE, userId));
    }

    public UserNotFoundException(String username) {
        super(String.format(MESSAGE, username));
    }
}
