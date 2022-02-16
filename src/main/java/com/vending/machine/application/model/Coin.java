package com.vending.machine.application.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.vending.machine.application.exception.CoinNotAcceptedException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Coin {
    COIN_100(100),
    COIN_50(50),
    COIN_20(20),
    COIN_10(10),
    COIN_5(5);

    @JsonValue
    private final Integer value;


    public static Coin fromValue(Integer coin) {
        return Arrays.stream(values())
                .filter(acceptedCoin -> acceptedCoin.getValue().equals(coin))
                .findFirst()
                .orElseThrow(() -> new CoinNotAcceptedException(coin));
    }

    public static List<Coin> change(Integer value) {
        List<Coin> rest = new ArrayList<>();
        if (value == 0) {
            return rest;
        }
        for (Coin coin : values()) {
            List<Coin> coins = getCoinsOfType(coin, value);
            if (coins.size() > 0) {
                rest.addAll(getCoinsOfType(coin, value));
                value %= coin.getValue();
            }
        }
        return rest;
    }

    private static List<Coin> getCoinsOfType(Coin coin, Integer restOfDeposit) {
        int coinNumber = restOfDeposit / coin.getValue();
        List<Coin> result = new ArrayList<>();
        for (int i = 0; i < coinNumber; i++) {
            result.add(coin);
        }
        return result;
    }

}