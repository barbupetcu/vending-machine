package com.vending.machine.api.controller;

import com.vending.machine.api.model.user.ChangeUserPasswordRequest;
import com.vending.machine.api.model.user.ChangeUserRolesRequest;
import com.vending.machine.api.model.user.CreateUserRequest;
import com.vending.machine.api.model.user.UserResponse;
import com.vending.machine.application.service.UserService;
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
public class UserController {

    public static final String CREATE_USER = "/user";
    private static final String CHANGE_USER_PASSWORD = "/user/password";
    private static final String CHANGE_USER_ROLES = "/user/{userId}/roles";

    private final UserService userService;

    @Operation(summary = "Create user")
    @PostMapping(CREATE_USER)
    public UserResponse createUser(@RequestBody @Validated CreateUserRequest request) {
        return userService.createUser(request);
    }

    @Operation(summary = "Change user password", security = @SecurityRequirement(name = OpenApiConfiguration.AUTHORIZATION_BEARER))
    @PutMapping(CHANGE_USER_PASSWORD)
    public void changePassword(Authentication authentication, @RequestBody @Validated ChangeUserPasswordRequest request) {
        userService.changePassword(authentication.getName(), request);
    }

    @Operation(summary = "Change user roles", security = @SecurityRequirement(name = OpenApiConfiguration.AUTHORIZATION_BEARER))
    @PutMapping(CHANGE_USER_ROLES)
    public void changeRoles(@PathVariable Long userId, @RequestBody @Validated ChangeUserRolesRequest request) {
        userService.changeRoles(userId, request);
    }
}
