package com.vending.machine.application.exception;

public class OldPasswordNotValidException extends RuntimeException {

    private static final String MESSAGE = "Old password provided for user %s is not valid";

    public OldPasswordNotValidException(String username) {
        super(String.format(MESSAGE, username));
    }
}
