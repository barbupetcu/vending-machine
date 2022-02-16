package com.vending.machine.api.controller;

import com.vending.machine.api.model.product.ProductRequestTestBuilder;
import com.vending.machine.application.exception.ProductNotFoundException;
import com.vending.machine.application.service.ProductService;
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
import static com.vending.machine.api.controller.ProductController.PRODUCT;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    private static final Long USER_ID = 1L;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private JwtUtil jwtUtil;

    @SpyBean
    private ProductService productService;

    private MockMvc mvc;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void createProductSuccessful() throws Exception {
        String jwtToken = jwtUtil.generateToken(USER_ID, Collections.singletonList(RoleType.SELLER));
        Integer cost = 5;
        Integer amountAvailable = 1;

        mvc.perform(
                        post(API_V1 + PRODUCT)
                                .with(BearerTokenRequestPostProcessor.bearerToken(jwtToken))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(ProductRequestTestBuilder.productRequestJson(cost, amountAvailable))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.sellerId", is(USER_ID.intValue())))
                .andExpect(jsonPath("$.cost", is(cost)))
                .andExpect(jsonPath("$.amountAvailable", is(1)));
    }

    @Test
    void createProductForbidden() throws Exception {
        String jwtToken = jwtUtil.generateToken(USER_ID, Collections.singletonList(RoleType.BUYER));

        mvc.perform(
                        post(API_V1 + PRODUCT)
                                .with(BearerTokenRequestPostProcessor.bearerToken(jwtToken))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(ProductRequestTestBuilder.productRequestJson(1, 1))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void createProductNotFound() throws Exception {
        String jwtToken = jwtUtil.generateToken(USER_ID, Collections.singletonList(RoleType.SELLER));

        doThrow(ProductNotFoundException.class).when(productService).createOrUpdateProduct(any());

        mvc.perform(
                        put(API_V1 + PRODUCT + "/1")
                                .with(BearerTokenRequestPostProcessor.bearerToken(jwtToken))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(ProductRequestTestBuilder.productRequestJson(1, 1))
                )
                .andExpect(status().isNotFound());
    }
}