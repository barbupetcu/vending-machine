package com.vending.machine.application.exception;

public class InvalidProductCostException extends RuntimeException {

    private static final String MESSAGE = "Given product cost %s is not multiplier of 5";

    public InvalidProductCostException(Integer givenCost) {
        super(String.format(MESSAGE, givenCost));
    }
}