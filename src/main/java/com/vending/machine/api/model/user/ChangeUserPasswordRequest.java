package com.vending.machine.api.model.user;

import lombok.Data;

@Data
public class ChangeUserPasswordRequest {
    private String oldPassword;
    private String newPassword;
}