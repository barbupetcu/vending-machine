package com.vending.machine.application.exception;

public class InsufficientProductStockException extends RuntimeException {

    private static final String MESSAGE = "Product stock %s is less than requested amount of products %s";

    public InsufficientProductStockException(Integer availableStock, Integer neededAmount) {
        super(String.format(MESSAGE, availableStock, neededAmount));
    }
}