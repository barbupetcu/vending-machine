package com.vending.machine.api.controller;

import com.vending.machine.api.model.deposit.DepositRequest;
import com.vending.machine.api.model.deposit.DepositResponse;
import com.vending.machine.application.service.TransactionService;
import com.vending.machine.infrastructure.OpenApiConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public DepositResponse createUser(Authentication authentication, @RequestBody @Validated DepositRequest request) {
        Integer total = transactionService.deposit(authentication.getName(), request.getCoin());
        return DepositResponse.withTotalAmount(total);
    }

    @Operation(summary = "Reset deposit", security = @SecurityRequirement(name = OpenApiConfiguration.AUTHORIZATION_BEARER))
    @PutMapping(RESET)
    public DepositResponse resetDeposit(Authentication authentication) {
        Integer total = transactionService.reset(authentication.getName());
        return DepositResponse.withTotalAmount(total);
    }
}
