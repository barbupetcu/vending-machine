package com.vending.machine.api.model.user;

import com.vending.machine.domain.model.RoleType;
import lombok.Data;

import java.util.List;

@Data
public class ChangeUserRolesRequest {
    private List<RoleType> roles;
}
