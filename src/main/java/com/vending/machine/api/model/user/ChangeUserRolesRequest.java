package com.vending.machine.api.model.user;

import lombok.Data;

import java.util.List;

@Data
public class ChangeUserRolesRequest {
    private List<String> roles;
}
