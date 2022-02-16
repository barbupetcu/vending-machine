package com.vending.machine.application.model;

import lombok.Getter;
import org.springframework.security.core.Authentication;

@Getter
public class AddDepositCommand extends UserRequest {

    private final Coin coin;

    public AddDepositCommand(Authentication user, Integer coinToAdd) {
        super(UserRequest.getUserId(user));
        this.coin = Coin.fromValue(coinToAdd);
    }
}
