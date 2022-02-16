package com.vending.machine.application.exception;

public class InsufficientDepositException extends RuntimeException {

    private static final String MESSAGE = "User deposit %s is less than needed amount for transaction %s";

    public InsufficientDepositException(Integer userDeposit, Integer neededAmount) {
        super(String.format(MESSAGE, userDeposit, neededAmount));
    }
}