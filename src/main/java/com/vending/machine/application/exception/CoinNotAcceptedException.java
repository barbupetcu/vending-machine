package com.vending.machine.application.exception;

public class CoinNotAcceptedException extends RuntimeException {

    private static final String MESSAGE = "Coin with value %s is not accepted";

    public CoinNotAcceptedException(Integer coinValue) {
        super(String.format(MESSAGE, coinValue));
    }
}