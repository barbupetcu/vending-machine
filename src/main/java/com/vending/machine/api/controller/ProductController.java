package com.vending.machine.api.controller;

import com.vending.machine.api.model.product.ProductRequest;
import com.vending.machine.application.model.ProductCommand;
import com.vending.machine.application.model.ProductCommandBuilder;
import com.vending.machine.application.model.ProductResult;
import com.vending.machine.application.service.ProductService;
import com.vending.machine.application.service.user.UserRoleService;
import com.vending.machine.infrastructure.OpenApiConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.vending.machine.api.ApiVersion.API_V1;

@RestController
@RequestMapping(API_V1)
@RequiredArgsConstructor
public class ProductController {

    public static final String PRODUCT = "/product";
    public static final String PRODUCTS = "/products";
    public static final String SPECIFIC_PRODUCT = "/product/{productId}";

    private final ProductService productService;

    @Operation(summary = "Create or update product", security = @SecurityRequirement(name = OpenApiConfiguration.AUTHORIZATION_BEARER))
    @PostMapping(PRODUCT)
    @PreAuthorize(UserRoleService.IS_SELLER)
    public ProductResult createProduct(Authentication authentication, @RequestBody @Valid ProductRequest requestBody) {
        ProductCommand productCommand = ProductCommandBuilder.builder()
                .setAuthentication(authentication)
                .setCost(requestBody.getCost())
                .setAmountAvailable(requestBody.getAmountAvailable())
                .setName(requestBody.getName())
                .createProductCommand();

        return productService.createOrUpdateProduct(productCommand);
    }

    @Operation(summary = "Create or update product", security = @SecurityRequirement(name = OpenApiConfiguration.AUTHORIZATION_BEARER))
    @PutMapping(SPECIFIC_PRODUCT)
    @PreAuthorize(UserRoleService.IS_SELLER)
    public ProductResult changeProduct(
            @PathVariable Long productId,
            Authentication authentication,
            @RequestBody @Valid ProductRequest requestBody) {
        ProductCommand productCommand = ProductCommandBuilder.builder()
                .setAuthentication(authentication)
                .setProductId(productId)
                .setCost(requestBody.getCost())
                .setAmountAvailable(requestBody.getAmountAvailable())
                .setName(requestBody.getName())
                .createProductCommand();

        return productService.createOrUpdateProduct(productCommand);
    }

    @Operation(summary = "Get product", security = @SecurityRequirement(name = OpenApiConfiguration.AUTHORIZATION_BEARER))
    @GetMapping(SPECIFIC_PRODUCT)
    public ProductResult getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @Operation(summary = "Get all products", security = @SecurityRequirement(name = OpenApiConfiguration.AUTHORIZATION_BEARER))
    @GetMapping(PRODUCTS)
    public List<ProductResult> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Delete product", security = @SecurityRequirement(name = OpenApiConfiguration.AUTHORIZATION_BEARER))
    @DeleteMapping(SPECIFIC_PRODUCT)
    @PreAuthorize(UserRoleService.IS_SELLER)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId) {
        productService.delete(productId);
    }
}