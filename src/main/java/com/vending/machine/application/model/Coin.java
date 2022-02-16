package com.vending.machine.application.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.vending.machine.application.exception.CoinNotAcceptedException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Coin {
    COIN_5(5),
    COIN_10(10),
    COIN_20(20),
    COIN_50(50),
    COIN_100(100);

    @JsonValue
    private final Integer value;


    public static Coin fromValue(Integer coin) {
        return Arrays.stream(values())
                .filter(acceptedCoin -> acceptedCoin.getValue().equals(coin))
                .findFirst()
                .orElseThrow(() -> new CoinNotAcceptedException(coin));
    }

    public static List<Coin> change(Integer value) {
        return null;
    }
}