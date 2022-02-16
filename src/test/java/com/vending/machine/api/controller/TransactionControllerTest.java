package com.vending.machine.api.controller;

import com.vending.machine.api.model.buy.BuyRequestTestBuilder;
import com.vending.machine.api.model.deposit.DepositRequestTestBuilder;
import com.vending.machine.application.exception.ProductNotFoundException;
import com.vending.machine.application.model.AddDepositCommand;
import com.vending.machine.application.model.BuyProductCommand;
import com.vending.machine.application.model.BuyProductResult;
import com.vending.machine.application.service.TransactionService;
import com.vending.machine.domain.model.RoleType;
import com.vending.machine.util.BearerTokenRequestPostProcessor;
import com.vending.machine.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static com.vending.machine.api.ApiVersion.API_V1;
import static com.vending.machine.api.controller.TransactionController.BUY;
import static com.vending.machine.api.controller.TransactionController.DEPOSIT;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerTest {

    private static final Long USER_ID = 1L;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private JwtUtil jwtUtil;

    @SpyBean
    private TransactionService transactionService;

    private MockMvc mvc;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void depositSuccessful() throws Exception {
        String jwtToken = jwtUtil.generateToken(USER_ID, Collections.singletonList(RoleType.BUYER));
        Integer coin = 5;

        doReturn(coin).when(transactionService).deposit(any(AddDepositCommand.class));

        mvc.perform(
                        post(API_V1 + DEPOSIT)
                                .with(BearerTokenRequestPostProcessor.bearerToken(jwtToken))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(DepositRequestTestBuilder.depositRequest(coin))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.userDeposit", is(coin)));
    }

    @Test
    void depositBadRequest() throws Exception {
        Integer coin = null;

        mvc.perform(
                        post(API_V1 + DEPOSIT)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(DepositRequestTestBuilder.depositRequest(coin))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void depositForbidden() throws Exception {
        String jwtToken = jwtUtil.generateToken(USER_ID, Collections.singletonList(RoleType.SELLER));

        mvc.perform(
                        post(API_V1 + DEPOSIT)
                                .with(BearerTokenRequestPostProcessor.bearerToken(jwtToken))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(DepositRequestTestBuilder.depositRequest(5))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void buyProductSuccessful() throws Exception {
        String jwtToken = jwtUtil.generateToken(USER_ID, Collections.singletonList(RoleType.BUYER));
        BuyProductResult buyProductResult = BuyProductResult.builder()
                .rest(10)
                .build();

        doReturn(buyProductResult).when(transactionService).buyProduct(any(BuyProductCommand.class));

        mvc.perform(
                        post(API_V1 + BUY)
                                .with(BearerTokenRequestPostProcessor.bearerToken(jwtToken))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(BuyRequestTestBuilder.buyRequestJson(1L,1))
                )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void buyProductForbidden() throws Exception {
        String jwtToken = jwtUtil.generateToken(USER_ID, Collections.singletonList(RoleType.SELLER));

        mvc.perform(
                        post(API_V1 + BUY)
                                .with(BearerTokenRequestPostProcessor.bearerToken(jwtToken))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(BuyRequestTestBuilder.buyRequestJson(1L,1))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void buyProductNotFound() throws Exception {
        String jwtToken = jwtUtil.generateToken(USER_ID, Collections.singletonList(RoleType.BUYER));

        doThrow(ProductNotFoundException.class).when(transactionService).buyProduct(any(BuyProductCommand.class));

        mvc.perform(
                        post(API_V1 + BUY)
                                .with(BearerTokenRequestPostProcessor.bearerToken(jwtToken))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(BuyRequestTestBuilder.buyRequestJson(1L,1))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void buyProductBadRequest() throws Exception {
        String jwtToken = jwtUtil.generateToken(USER_ID, Collections.singletonList(RoleType.BUYER));

        doThrow(ProductNotFoundException.class).when(transactionService).buyProduct(any(BuyProductCommand.class));

        mvc.perform(
                        post(API_V1 + BUY)
                                .with(BearerTokenRequestPostProcessor.bearerToken(jwtToken))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(BuyRequestTestBuilder.buyRequestJson(null,null))
                )
                .andExpect(status().isBadRequest());
    }
}