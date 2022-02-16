package com.vending.machine.api.controller;

import com.vending.machine.api.model.buy.BuyRequest;
import com.vending.machine.api.model.buy.BuyResponse;
import com.vending.machine.api.model.deposit.DepositRequest;
import com.vending.machine.api.model.deposit.DepositResponse;
import com.vending.machine.application.model.*;
import com.vending.machine.application.service.TransactionService;
import com.vending.machine.application.service.user.UserRoleService;
import com.vending.machine.infrastructure.OpenApiConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.vending.machine.api.ApiVersion.API_V1;

@RestController
@RequestMapping(API_V1)
@RequiredArgsConstructor
public class TransactionController {

    private static final String DEPOSIT = "/deposit";
    private static final String BUY = "/buy";
    private static final String RESET = "/reset";

    private final TransactionService transactionService;


    @Operation(summary = "Deposit coins", security = @SecurityRequirement(name = OpenApiConfiguration.AUTHORIZATION_BEARER))
    @PostMapping(DEPOSIT)
    @PreAuthorize(UserRoleService.IS_BUYER)
    public DepositResponse depositCoins(Authentication authentication, @RequestBody @Validated DepositRequest request) {
        AddDepositCommand depositChange = new AddDepositCommand(authentication, request.getCoin());
        Integer total = transactionService.deposit(depositChange);
        return DepositResponse.withTotalAmount(total);
    }

    @Operation(summary = "Reset deposit", security = @SecurityRequirement(name = OpenApiConfiguration.AUTHORIZATION_BEARER))
    @PutMapping(RESET)
    @PreAuthorize(UserRoleService.IS_BUYER)
    public DepositResponse resetDeposit(Authentication authentication) {
        UserRequest transactionRequest = UserRequest.build(authentication);
        Integer total = transactionService.reset(transactionRequest);
        return DepositResponse.withTotalAmount(total);
    }

    @Operation(summary = "Buy product", security = @SecurityRequirement(name = OpenApiConfiguration.AUTHORIZATION_BEARER))
    @PostMapping(BUY)
    @PreAuthorize(UserRoleService.IS_BUYER)
    public BuyResponse buyProduct(Authentication authentication, @RequestBody @Valid BuyRequest buyRequest) {
        BuyProductCommand buyProductCommand = new BuyProductCommand(authentication, buyRequest.getProductId(), buyRequest.getAmountOfProducts());
        BuyProductResult buyProductResult = transactionService.buyProduct(buyProductCommand);
        return BuyResponse.builder()
                .totalSpent(buyProductResult.getTotalSpent())
                .productName(buyProductResult.getProductName())
                .rest(Coin.change(buyProductResult.getRest()))
                .build();
    }
}
