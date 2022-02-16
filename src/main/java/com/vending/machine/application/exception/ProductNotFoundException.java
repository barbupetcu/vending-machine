package com.vending.machine.application.exception;

public class ProductNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Product with id %s doesn't exists";

    public ProductNotFoundException(Long userId) {
        super(String.format(MESSAGE, userId));
    }
}