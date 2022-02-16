package com.vending.machine.api.controller;

import com.vending.machine.api.model.AuthenticationResponse;
import com.vending.machine.api.model.user.ChangeUserPasswordRequest;
import com.vending.machine.api.model.user.ChangeUserRolesRequest;
import com.vending.machine.api.model.user.CreateUserRequest;
import com.vending.machine.api.model.user.UserResponse;
import com.vending.machine.application.model.ChangePasswordCommand;
import com.vending.machine.application.model.ChangeRolesCommand;
import com.vending.machine.application.service.AuthenticationService;
import com.vending.machine.application.service.user.UserService;
import com.vending.machine.infrastructure.OpenApiConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.vending.machine.api.ApiVersion.API_V1;

@RestController
@RequestMapping(API_V1)
@RequiredArgsConstructor
public class UserController {

    public static final String CREATE_USER = "/user";
    private static final String CHANGE_USER_PASSWORD = "/user/password";
    private static final String CHANGE_USER_ROLES = "/user/roles";

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Create user")
    @PostMapping(CREATE_USER)
    public UserResponse createUser(@RequestBody @Valid CreateUserRequest request) {
        return userService.createUser(request);
    }

    @Operation(summary = "Change user password", security = @SecurityRequirement(name = OpenApiConfiguration.AUTHORIZATION_BEARER))
    @PatchMapping(CHANGE_USER_PASSWORD)
    public void changePassword(Authentication authentication, @RequestBody @Valid ChangeUserPasswordRequest request) {
        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand(authentication, request.getOldPassword(), request.getNewPassword());
        userService.changePassword(changePasswordCommand);
    }

    @Operation(summary = "Change user roles", security = @SecurityRequirement(name = OpenApiConfiguration.AUTHORIZATION_BEARER))
    @PatchMapping(CHANGE_USER_ROLES)
    public AuthenticationResponse changeRoles(
            Authentication authentication,
            @RequestBody @Valid ChangeUserRolesRequest changeUserRolesRequest
    ) {
        ChangeRolesCommand changeRolesCommand = new ChangeRolesCommand(authentication, changeUserRolesRequest.getRoles());
        userService.changeRoles(changeRolesCommand);
        String newToken = authenticationService.refreshToken(changeRolesCommand.getUserId());
        return AuthenticationResponse.withToken(newToken);

    }
}
