package com.vending.machine.api.model.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ChangeUserRolesRequest {
    @NotNull(message = "roles must by not null")
    @NotEmpty(message = "roles must not be empty")
    private List<String> roles;
}
