package com.vending.machine.application.exception;

public class InvalidRoleException extends RuntimeException {

    private static final String MESSAGE = "Given role %s is not valid";

    public InvalidRoleException(String role) {
        super(String.format(MESSAGE, role));
    }
}
