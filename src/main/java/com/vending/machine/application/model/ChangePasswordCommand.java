package com.vending.machine.application.model;

import lombok.Getter;
import org.springframework.security.core.Authentication;

@Getter
public class ChangePasswordCommand extends UserRequest {

    private final String oldPassword;
    private final String newPassword;

    public ChangePasswordCommand(Authentication user, String oldPassword, String newPassword) {
        super(UserRequest.getUserId(user));
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
