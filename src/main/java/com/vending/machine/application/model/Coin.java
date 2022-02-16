package com.vending.machine.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Coin {
    COIN_5(5),
    COIN_10(10),
    COIN_20(20),
    COIN_50(50),
    COIN_100(100);

    private final Integer value;

    public static boolean isAccepted(Integer coin) {
        return Arrays.stream(values()).map(Coin::getValue).anyMatch(acceptedCoin -> acceptedCoin.equals(coin));
    }
}