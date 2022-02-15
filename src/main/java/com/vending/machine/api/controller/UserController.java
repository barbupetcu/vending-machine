package com.vending.machine.api.controller;

import com.vending.machine.api.model.user.ChangeUserPasswordRequest;
import com.vending.machine.api.model.user.ChangeUserRolesRequest;
import com.vending.machine.api.model.user.CreateUserRequest;
import com.vending.machine.api.model.user.UserResponse;
import com.vending.machine.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.vending.machine.api.ApiVersion.API_V1;

@RestController
@RequestMapping(API_V1)
@RequiredArgsConstructor
public class UserController {

    private static final String CREATE_USER = "/user";
    private static final String CHANGE_USER_PASSWORD = "/user/{userId}/password";
    private static final String CHANGE_USER_ROLES = "/user/{userId}/roles";
    private static final String DELETE_USER = "/user/{userId}";

    private final UserService userService;

    @Operation(summary = "Create user")
    @PostMapping(CREATE_USER)
    public UserResponse createUser(@RequestBody @Validated CreateUserRequest request) {
        return userService.createUser(request);
    }

    @Operation(summary = "Change user password")
    @PutMapping(CHANGE_USER_PASSWORD)
    public void changePassword(@PathVariable Long userId, @RequestBody @Validated ChangeUserPasswordRequest request) {
        userService.changePassword(userId, request);
    }

    @Operation(summary = "Change user roles")
    @PutMapping(CHANGE_USER_ROLES)
    public void changeRoles(@PathVariable Long userId, @RequestBody @Validated ChangeUserRolesRequest request) {
        userService.changeRoles(userId, request);
    }

    @Operation(summary = "Delete user")
    @DeleteMapping(DELETE_USER)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeRoles(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
